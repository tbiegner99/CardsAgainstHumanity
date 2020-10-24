import React from 'react';
import PlayerCard from './PlayerCard';

const renderSortedPlayers = (props, players) =>
  players.map((player) => {
    const { index, playerId } = player;
    const { width, height } = props;
    let x = window.innerWidth - width;
    let y = 0.07 * (index - 1) * props.height;
    if (index === 0) {
      x = (window.innerWidth - width) / 2 - width / 2;
      y = 0;
    }
    return (
      <PlayerCard key={playerId} player={player} width={width} height={0.07 * height} x={x} y={y} />
    );
  });

const PlayerList = (props) => {
  const players = props.players.map((player, index) => ({ ...player, index }));
  players.sort((player1, player2) => player1.playerId - player2.playerId);
  return renderSortedPlayers(props, players);
};

export default PlayerList;
