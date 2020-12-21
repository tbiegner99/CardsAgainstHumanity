import React from 'react';
import { SortDirection, sortFunctionByField } from '../../utils/SortFunctions';
import styles from './playerBoard.css';

export const DataHeaders = {
  NAME: 'Player',
  CZAR_ORDER: 'Order',
  SCORE: 'Score'
};

export { SortDirection };

export const DataFields = {
  CZAR_INDEX: 'czarIndex',
  SCORE: 'score',
  NAME: 'name'
};

const BoardHeaders = (props) => (
  <thead>
    <tr>
      <th className={styles.rankColumn}>#</th>
      <th className={styles.nameColumn}>{DataHeaders.NAME}</th>
      <th className={styles.scoreColumn}>{props.header || DataHeaders.SCORE}</th>
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

  const sortFunction = sortFunctionByField(sortField, sortDirection);
  const sortedData = copyData.sort(sortFunction);
  return sortedData;
};

const renderTitle = (title) => <h3 className={styles.title}>{title}</h3>;

const PlayerBoard = (props) => {
  const {
    sortDirection,
    sortField,
    dataField,
    playerData,
    title,
    header = DataHeaders.SCORE
  } = props;
  if (!playerData) {
    return null;
  }
  const sortedData =
    sortField == null ? playerData : sortBoardData(sortDirection, sortField, playerData);
  return (
    <div>
      {renderTitle(title)}
      <table className={styles.playerBoard}>
        <BoardHeaders header={header} valueField={sortField} />
        <tbody>
          {sortedData.map((data, index) => (
            <PlayerTile
              index={index + 1}
              icon={null}
              name={data.name}
              value={data[dataField || sortField]}
            />
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default PlayerBoard;
