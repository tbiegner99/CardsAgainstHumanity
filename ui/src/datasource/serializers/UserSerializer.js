class UserSerializer {
  serializeCreateUserData(data) {
    const { displayName, email, password, firstName, lastName } = data;
    return {
      displayName,
      email,
      password,
      firstName,
      lastName
    };
  }

  deserializeUserData(player) {
    const { id, displayName, email, firstName, lastName } = player;
    return {
      id,
      displayName,
      email,
      firstName,
      lastName
    };
  }
}

export default new UserSerializer();
