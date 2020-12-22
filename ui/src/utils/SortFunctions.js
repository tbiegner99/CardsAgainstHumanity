// eslint-disable-next-line import/prefer-default-export
export const SortDirection = {
  ASCENDING: 'asc',
  DESCENDING: 'desc'
};
export const sortFunctionByField = (sortField, sortDirection) => {
  const directionMultiplier = sortDirection === SortDirection.DESCENDING ? -1 : 1;
  return (data1, data2) => directionMultiplier * (data1[sortField] - data2[sortField]);
};

export const sortObjectsByFieldIgnoreCase = (fieldName) => (object1, object2) => {
  if (object1[fieldName].toLowerCase() > object2[fieldName].toLowerCase()) {
    return 1;
  } else if (object1[fieldName].toLowerCase() === object2[fieldName].toLowerCase()) {
    return 0;
  }
  return -1;
};

export const orderByCzarStartingWithCurrent = (players) => {
  if (!players) {
    return null;
  }
  const sortFunc = sortFunctionByField('czarIndex', SortDirection.ASCENDING);
  const sorted = [...players].sort(sortFunc);
  const currentCzarIndex = sorted.findIndex((player) => player.currentCzar);
  if (currentCzarIndex < 0) {
    return sorted;
  }
  return [...sorted.slice(currentCzarIndex), ...sorted.slice(0, currentCzarIndex)];
};
