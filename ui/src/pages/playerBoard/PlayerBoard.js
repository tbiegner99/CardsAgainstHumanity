import React from 'react';

import styles from './playerBoard.css';

const DataHeaders = {
  NAME: 'Player',
  CZAR_ORDER: 'Order',
  SCORE: 'Score'
};

export const DataFields = {
  CZAR_ORDER: 'czarOrder',
  SCORE: 'score',
  NAME: 'name'
};
export const SortDirection = {
  ASCENDING: 'asc',
  DESCENDING: 'desc'
};

const BoardHeaders = () => (
  <thead>
    <tr>
      <th className={styles.rankColumn}>#</th>
      <th className={styles.nameColumn}>{DataHeaders.NAME}</th>
      <th className={styles.scoreColumn}>{DataHeaders.SCORE}</th>
    </tr>
  </thead>
);

const PlayerTile = (props) => (
  <tr className={styles.playerTile}>
    <td className={styles.rankColumn}>{props.index}</td>
    <td className={styles.nameColumn}>{props.name}</td>
    <td className={styles.scoreColumn}>{props.value}</td>
  </tr>
);

const sortBoardData = (sortDirection, sortField, playerData) => {
  const copyData = [].concat(playerData);
  const directionMultiplier = sortDirection === SortDirection.DESCENDING ? -1 : 1;
  const sortFunction = (data1, data2) =>
    directionMultiplier * (data1[sortField] - data2[sortField]);
  const sortedData = copyData.sort(sortFunction);
  return sortedData;
};

const PlayerBoard = (props) => {
  const { sortDirection, sortField, playerData } = props;
  const sortedData = sortBoardData(sortDirection, sortField, playerData);
  return (
    <table className={styles.playerBoard}>
      <BoardHeaders valueField={sortField} />
      <tbody>
        {sortedData.map((data, index) => (
          <PlayerTile index={index + 1} icon={null} name={data.name} value={data[sortField]} />
        ))}
      </tbody>
    </table>
  );
};

export default PlayerBoard;
