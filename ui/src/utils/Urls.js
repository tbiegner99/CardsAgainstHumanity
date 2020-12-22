const parameterize = (url, params) => {
  let replacedUrl = url;
  Object.entries(params).forEach(([key, value]) => {
    const regex = new RegExp(`:${key}`, 'g');
    replacedUrl = replacedUrl.replace(regex, value);
  });
  return replacedUrl;
};

const CONTEXT_ROOT = '/cah';
const fullUrl = (url) => `${CONTEXT_ROOT}${url}`;
const HOME = fullUrl('/auth/home');

export default {
  PLAY_GAME: fullUrl('/auth/play/:gameId'),
  SCOREBOARD: fullUrl('/auth/play/:gameId/scoreboard'),
  CZAR_ORDER: fullUrl('/auth/play/:gameId/czarOrder'),
  MY_HAND: fullUrl('/auth/play/:gameId/hand'),
  POST_LOGIN_URL: HOME,
  AUTHENTICATED: fullUrl('/auth'),
  HOME,
  LOGIN: fullUrl('/login'),
  REGISTER: fullUrl('/register'),
  LOGOUT: fullUrl('/logout'),
  NEW_GAME: fullUrl('/auth/game'),
  JOIN_GAME: fullUrl('/auth/join'),
  DESIGN: fullUrl('/auth/design'),
  DESIGN_PACKAGES: fullUrl('/auth/design/packages'),
  EDIT_PACKAGE: fullUrl('/auth/design/package/:packageId'),
  DESIGN_DECK: fullUrl('/auth/design/deck'),
  EDIT_DECK: fullUrl('/auth/design/deck/:deckId'),
  STATS: fullUrl('/auth/stats'),
  Audience: {
    HOME: fullUrl('/audience'),
    JOIN: fullUrl('/audience/join'),
    GAME: fullUrl('/audience/view/:gameId')
  },

  parameterize
};
