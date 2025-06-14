import { configureStore } from '@reduxjs/toolkit';
import createSagaMiddleware from 'redux-saga';
import appReducer from 'Reducers/appReducer';
import rootSaga from './sagas/rootSaga';
import exp from 'constants';

const sagaMiddleware = createSagaMiddleware();

const store = configureStore({
  reducer: {
    // @ts-ignore to fix in future
    app: appReducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({ thunk: false }).concat(sagaMiddleware),
  devTools: process.env.NODE_ENV !== 'production'
});

sagaMiddleware.run(rootSaga);

// types
export type RootState = ReturnType<typeof store.getState>;
export type AppState = ReturnType<typeof appReducer>;
export type AppAction = Parameters<typeof appReducer>[1];
export type AppDispatch = typeof store.dispatch;

export default store;
