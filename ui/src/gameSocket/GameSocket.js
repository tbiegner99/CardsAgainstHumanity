import WebSocket from './WebSocket';
import CommandSerializer from '../datasource/serializers/CommandSerializer';

const DEFAULT_RESPONSE_GENERATOR = () => ({
  status: 204,
  data: null
});

const isCommandResponse = ({ data }) => data.messageType === 'RESPONSE';
const isCommand = ({ data }) => data.messageType === 'COMMAND';
const responseHasId = ({ data }, id) => data.response.messageId === id;
const getCommandFilter = () => ({ eventData }) => isCommand(eventData);
const getResponseFilter = (commandId) => ({ eventData }) =>
  isCommandResponse(eventData) && responseHasId(eventData, commandId);

const PacketDeserializer = (rawData) => {
  const packetJson = JSON.parse(rawData);
  return {
    ...packetJson,
    response: packetJson.body
  };
};

class GameSocket extends WebSocket {
  constructor(url) {
    super(url);
    this.commandListeners = [];
    this.responseGenerators = {};
    this.addListener(getCommandFilter(), this.handleCommand.bind(this));
  }

  findCommandListeners(commandData) {
    return this.commandListeners
      .filter(({ filter }) => filter(commandData))
      .map(({ listener }) => listener);
  }

  findCommandResponseGenerator(commandData) {
    const { commandName } = commandData;
    return this.responseGenerators[commandName] || DEFAULT_RESPONSE_GENERATOR;
  }

  async handleCommand(packetData) {
    const commandData = packetData.data.body;
    console.log(commandData, packetData);
    let serializedResponse;
    try {
      const listeners = this.findCommandListeners(commandData);
      const promises = listeners.map((listener) => listener(commandData));
      await Promise.all(promises);
      const responseGenerator = this.findCommandResponseGenerator(commandData);
      const responseData = await responseGenerator(commandData);
      serializedResponse = CommandSerializer.serializeCommandResponse(commandData, responseData);
    } catch (err) {
      console.log(err);
      serializedResponse = CommandSerializer.serializeErrorResponse(commandData, err);
    }

    return this.sendData(JSON.stringify(serializedResponse));
  }

  get deserializer() {
    return PacketDeserializer;
  }

  setResponseGenerator(commandName, generator) {
    this.responseGenerators[commandName] = generator;
  }

  addCommandHandler(commandName, listener) {
    const filter = (command) => command.commandName === commandName;
    this.commandListeners.push({
      filter,
      listener
    });
  }

  sendCommand(commandName, data) {
    const rawCommand = CommandSerializer.serializeCommands(commandName, data);

    return new Promise(async (resolve, reject) => {
      try {
        const filter = getResponseFilter(rawCommand.messageId);
        this.addOneTimeListener(filter, resolve);
        const commandPacket = CommandSerializer.serializeCommandPacket(rawCommand);
        await this.sendData(JSON.stringify(commandPacket));
      } catch (err) {
        reject(err);
      }
    });
  }
}

export default GameSocket;
