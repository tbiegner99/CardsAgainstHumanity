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
  POST_LOGIN_URL: HOME,
  HOME,
  LOGIN: '/login',
  LOGOUT: '/logout',
  NEW_GAME: '/auth/game',
  JOIN_GAME: '/auth/join',
  DESIGN_DECK: '/auth/design',
  STATS: '/auth/stats',
  Audience: {
    HOME: '/audience',
    JOIN: '/audience/join',
    GAME: '/audience/view/:gameId'
  },

  parameterize
};
