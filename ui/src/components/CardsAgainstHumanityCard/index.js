import combineClasses from 'classnames';
import React from 'react';
import { FlipCard, FrontFace, BackFace } from '../FlipCard';

import style from './index.css';

const Instructions = {
  DRAW2_PICK3: 'draw2pick3',
  PICK2: 'pick2'
};

const renderPackIcon = () => (
  <svg
    className={style.packIconSvg}
    height="15px"
    width="15px"
    fill="#000000"
    version="1.1"
    x="0px"
    y="0px"
    viewBox="0 0 100 100"
    enableBackground="new 0 0 100 100"
    xmlSpace="preserve"
  >
    <path
      stroke="black"
      fill="white"
      d="M94.021,11.858c-0.303-0.386-0.737-0.63-1.222-0.689l-49.98-6.022c-0.037-0.004-0.182-0.013-0.219-0.013  c-0.927,0-1.71,0.694-1.821,1.615l-1.017,8.439L7.081,27.119c-0.46,0.168-0.827,0.505-1.033,0.95  c-0.207,0.445-0.228,0.943-0.061,1.402l23.482,64.323c0.263,0.721,0.956,1.205,1.725,1.205c0.214,0,0.425-0.037,0.627-0.111  l37.895-13.835l14.455,1.741c0.037,0.004,0.182,0.013,0.219,0.013c0.927,0,1.71-0.694,1.821-1.615l8.191-67.985  C94.459,12.723,94.325,12.243,94.021,11.858z M31.214,93.117L7.722,28.877l31.793-11.633l-6.926,57.49  c-0.121,1.004,0.598,1.919,1.601,2.04l31.422,3.785L31.214,93.117z"
    />
  </svg>
);
const renderDraw2 = () => (
  <div className="instruction">
    PICK <span className="numberCircle">2</span>
  </div>
);

const renderDraw2Pick3 = () => (
  <div>
    <div className={style.instruction}>
      DRAW <span className={style.numberCircle}>2</span>
    </div>
    <div className={style.instruction}>
      PICK <span className={style.numberCircle}>3</span>
    </div>
  </div>
);

const getInstructionComponent = (instruction) => {
  if (!instruction) return null;
  switch (instruction.toLowerCase()) {
    case Instructions.DRAW2_PICK3:
      return renderDraw2Pick3();
    case Instructions.PICK2:
      return renderDraw2();
    default:
      return null;
  }
};

const CardInstruction = (props) => {
  const { instruction } = props;
  const instructionComponent = getInstructionComponent(instruction);
  return <div className={style.specialInstruction}>{instructionComponent}</div>;
};

const CardText = (props) => <div>{props.children}</div>;

const CardFooter = (props) => (
  <div className={style.bottomRow}>
    <div className={style.pack}>
      <div className={style.packIcon}>{renderPackIcon()}</div>
      <div className={style.packTitle}>Cards Against Humanity</div>
    </div>
    <CardInstruction instruction={props.instruction} />
  </div>
);

const CardsAgainsHumanityCardBack = () => <div>Cards Against Humanity</div>;

const CardsAgainstHumanityCard = (props) => {
  const { card, face, backFace, frontFace } = style;
  const cardClasses = combineClasses(card, props.className);
  return (
    <FlipCard {...props} className={cardClasses}>
      <FrontFace className={combineClasses(face, props.faceClass, props.frontFaceClass, frontFace)}>
        <CardText>{props.children}</CardText>
        <CardFooter instruction={props.instruction} />
      </FrontFace>
      <BackFace className={combineClasses(face, props.faceClass, props.frontFaceClass, backFace)}>
        <CardsAgainsHumanityCardBack />
      </BackFace>
    </FlipCard>
  );
};

const WhiteCard = (props) => <CardsAgainstHumanityCard {...props} faceClass={style.whiteCard} />;

const BlackCard = (props) => <CardsAgainstHumanityCard {...props} faceClass={style.blackCard} />;

export { WhiteCard, BlackCard };
