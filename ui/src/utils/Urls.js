const HOME = '/auth/home';

const parameterize = (url, params) => {
  let replacedUrl = url;
  Object.entries(params).forEach(([key, value]) => {
    const regex = new RegExp(`:${key}`, 'g');
    replacedUrl = replacedUrl.replace(regex, value);
  });
  return replacedUrl;
};

export default {
  PLAY_GAME: '/auth/play/:gameId',
  SCOREBOARD: '/auth/play/:gameId/scoreboard',
  CZAR_ORDER: '/auth/play/:gameId/czarOrder',
  MY_HAND: '/auth/play/:gameId/hand',
  POST_LOGIN_URL: HOME,
  HOME,
  LOGIN: '/login',
  REGISTER: '/register',
  LOGOUT: '/logout',
  NEW_GAME: '/auth/game',
  JOIN_GAME: '/auth/join',
  DESIGN: '/auth/design',
  DESIGN_PACKAGES: '/auth/design/packages',
  EDIT_PACKAGE: '/auth/design/package/:packageId',
  DESIGN_DECK: '/auth/design/deck',
  EDIT_DECK: '/auth/design/deck/:deckId',
  STATS: '/auth/stats',
  Audience: {
    HOME: '/audience',
    JOIN: '/audience/join',
    GAME: '/audience/view/:gameId'
  },

  parameterize
};
