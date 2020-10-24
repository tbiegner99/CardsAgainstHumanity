const NORMAL_SOCKET_CLOSE = 1000;
const NORMAL_CLOSE_REASON = 'Socket closed normally';

const IdentityDeserializer = (rawData) => rawData;
const JsonDeserializer = (rawData) => JSON.parse(rawData);

class WebSocketManager {
  constructor(url) {
    this.connected = false;
    this.closed = false;
    this.url = url;
    this.listeners = [];
    this._deserializer = IdentityDeserializer;
  }

  async connect() {
    if (this.isClosed) {
      throw new Error('Connection has been closed');
    }
    return new Promise((resolve, reject) => {
      this.socket = new WebSocket(this.url);
      this.socket.onopen = () => {
        this.connected = true;
        resolve();
      };
      this.socket.onmessage = (evt) => {
        this.handleWebsocketDataReceived(evt.data);
      };
      this.socket.onerror = reject;
      this.socket.onClose = this.onConnectionLost;
    });
  }

  get isClosed() {
    return this.closed;
  }

  get isConnected() {
    return this.connected;
  }

  get deserializer() {
    return this._deserializer;
  }

  set deserializer(deserializer) {
    this._deserializer = deserializer;
  }

  set reconnectStrategy(reconnectStrategy) {
    this.reconnectStrategy = reconnectStrategy;
  }

  onConnectionLost(e) {
    this.connected = false;
    if (e.reasonCode === NORMAL_SOCKET_CLOSE) {
      this.socket = null;
      this.closed = true;
    } else if (this.reconnectStrategy) {
      this.reconnectStrategy.reconnect(this);
    }
  }

  onSocketError() {}

  on(eventName, func) {
    const filterOnName = (event) => event.name === eventName;
    this.addListener(filterOnName, func);
  }

  removeExhaustedListeners() {
    for (let i = this.listeners.length - 1; i >= 0; i--) {
      if (this.listeners[i].times === 0) {
        this.listeners.splice(i, 1);
      }
    }
  }

  dispatchEvent(name, data) {
    const eventObject = { name, eventData: data };
    for (let i = 0; i < this.listeners.length; i++) {
      const listenerObj = this.listeners[i];
      const { filter, listener } = listenerObj;
      if (filter(eventObject)) {
        listener(data);
        listenerObj.times--;
        listenerObj.calls++;
      }
    }
    this.removeExhaustedListeners();
  }

  addListener(filter, listener, times = -1) {
    this.listeners.push({
      filter,
      times,
      calls: 0,
      listener
    });
  }

  addOneTimeListener(filter, listener) {
    this.addListener(filter, listener, 1);
  }

  async sendData(data) {
    if (this.connected) {
      this.socket.send(data);
    } else {
      throw Error('Illegal State: cant send data if not connected');
    }
  }

  handleWebsocketDataReceived(rawData) {
    const data = this.deserializer(rawData);
    this.dispatchEvent('data', { rawData, data });
  }

  close() {
    if (this.socket) {
      this.socket.close(NORMAL_SOCKET_CLOSE, NORMAL_CLOSE_REASON);
    }
  }
}

export { IdentityDeserializer, JsonDeserializer };

export default WebSocketManager;
