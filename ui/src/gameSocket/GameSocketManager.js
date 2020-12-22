import GameSocket from './GameSocket';

const CONNECTION_URL = `ws://${window.location.host}/api/cah/socket/playerConnection`;

class GameSocketManager {
  static gameSocket = null;

  static getGameSocket() {
    if (!this.gameSocket) {
      this.gameSocket = new GameSocket(CONNECTION_URL);
    }
    return this.gameSocket;
  }

  static addCommandHandler(commandName, handler) {
    this.getGameSocket().addCommandHandler(commandName, handler);
  }

  static setResponseGenerator(commandName, generator) {
    this.getGameSocket().setResponseGenerator(commandName, generator);
  }

  static async ensureConnected() {
    const socket = this.getGameSocket();
    if (!socket.isConnected) {
      await socket.connect();
    }
  }
}

export default GameSocketManager;
