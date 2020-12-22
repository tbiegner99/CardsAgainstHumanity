const BLANK_REGEX = /_+/;

const hasBlank = (str) => BLANK_REGEX.test(str);

const isQuestion = (card) => !hasBlank(card.text);

const replaceNextBlank = (str, text) => str.replace(BLANK_REGEX, text);

const answerQuestion = (blackCard, cards) => {
  const answers = cards.map((card) => card.text).join(' ');
  return [blackCard.text, answers].join(' ');
};

const previewPlay = (blackCard, cards) => {
  let str = blackCard.text;

  if (isQuestion(blackCard)) {
    return answerQuestion(blackCard, cards);
  }
  for (let playIndex = 0; playIndex < cards.length && hasBlank(str); playIndex++) {
    str = replaceNextBlank(str, cards[playIndex].text);
  }

  return str;
};

export default {
  previewPlay
};
