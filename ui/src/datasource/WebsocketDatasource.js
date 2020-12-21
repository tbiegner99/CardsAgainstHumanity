import GameSocketManager from '../gameSocket/GameSocketManager';
import CommandSerializer from './serializers/CommandSerializer';

class WebsocketDatasource {
  async ensureConnected() {
    await GameSocketManager.ensureConnected();
  }

  addCommandHandler(commandName, handler) {
    const deserializationHandler = (commandData) => {
      const deserializedCommand = CommandSerializer.deserializeCommand(commandName, commandData);
      handler(deserializedCommand);
    };
    GameSocketManager.addCommandHandler(commandName, deserializationHandler);
  }

  setResponseGenerator(commandName, generator) {
    GameSocketManager.setResponseGenerator(commandName, generator);
  }

  async sendCommand(commandName, data) {
    await this.ensureConnected();
    const commandResponse = await GameSocketManager.getGameSocket().sendCommand(commandName, data);
    return CommandSerializer.deserializeResponse(commandName, commandResponse);
  }
}

export default new WebsocketDatasource();
