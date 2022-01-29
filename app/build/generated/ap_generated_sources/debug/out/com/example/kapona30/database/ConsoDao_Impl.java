package com.example.kapona30.database;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ConsoDao_Impl implements ConsoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ConsoData> __insertionAdapterOfConsoData;

  private final EntityDeletionOrUpdateAdapter<ConsoData> __deletionAdapterOfConsoData;

  private final SharedSQLiteStatement __preparedStmtOfConsoMajDB;

  private final SharedSQLiteStatement __preparedStmtOfConsoMajDBJan;

  private final SharedSQLiteStatement __preparedStmtOfDeleteCode;

  public ConsoDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfConsoData = new EntityInsertionAdapter<ConsoData>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `table` (`ID`,`quantite`,`code_barres`,`numero_jour`,`numero_semaine`,`numero_mois`,`nature`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ConsoData value) {
        stmt.bindLong(1, value.getID());
        stmt.bindDouble(2, value.getQuantite());
        if (value.getCodeBarres() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCodeBarres());
        }
        stmt.bindLong(4, value.getNumeroJour());
        stmt.bindLong(5, value.getNumeroSemaine());
        stmt.bindLong(6, value.getNumeroMois());
        if (value.getNature() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getNature());
        }
      }
    };
    this.__deletionAdapterOfConsoData = new EntityDeletionOrUpdateAdapter<ConsoData>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `table` WHERE `ID` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ConsoData value) {
        stmt.bindLong(1, value.getID());
      }
    };
    this.__preparedStmtOfConsoMajDB = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM `table` WHERE numero_mois < ? AND nature = ?";
        return _query;
      }
    };
    this.__preparedStmtOfConsoMajDBJan = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM `table` WHERE numero_mois < ? AND numero_mois > ? AND nature = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteCode = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM `table` WHERE code_barres = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final ConsoData consoData) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfConsoData.insert(consoData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final ConsoData consoData) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfConsoData.handle(consoData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void reset(final List<ConsoData> consoDataList) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfConsoData.handleMultiple(consoDataList);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void consoMajDB(final int sNumeroMois, final String sNature) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfConsoMajDB.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, sNumeroMois);
    _argIndex = 2;
    if (sNature == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, sNature);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfConsoMajDB.release(_stmt);
    }
  }

  @Override
  public void consoMajDBJan(final int sNumeroMois, final int sNumeroMois2, final String sNature) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfConsoMajDBJan.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, sNumeroMois);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, sNumeroMois2);
    _argIndex = 3;
    if (sNature == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, sNature);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfConsoMajDBJan.release(_stmt);
    }
  }

  @Override
  public void deleteCode(final String sCode_barres) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteCode.acquire();
    int _argIndex = 1;
    if (sCode_barres == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, sCode_barres);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteCode.release(_stmt);
    }
  }

  @Override
  public List<ConsoData> getAll(final String sNature) {
    final String _sql = "SELECT * FROM `table` WHERE nature = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (sNature == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, sNature);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfID = CursorUtil.getColumnIndexOrThrow(_cursor, "ID");
      final int _cursorIndexOfQuantite = CursorUtil.getColumnIndexOrThrow(_cursor, "quantite");
      final int _cursorIndexOfCodeBarres = CursorUtil.getColumnIndexOrThrow(_cursor, "code_barres");
      final int _cursorIndexOfNumeroJour = CursorUtil.getColumnIndexOrThrow(_cursor, "numero_jour");
      final int _cursorIndexOfNumeroSemaine = CursorUtil.getColumnIndexOrThrow(_cursor, "numero_semaine");
      final int _cursorIndexOfNumeroMois = CursorUtil.getColumnIndexOrThrow(_cursor, "numero_mois");
      final int _cursorIndexOfNature = CursorUtil.getColumnIndexOrThrow(_cursor, "nature");
      final List<ConsoData> _result = new ArrayList<ConsoData>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ConsoData _item;
        _item = new ConsoData();
        final int _tmpID;
        _tmpID = _cursor.getInt(_cursorIndexOfID);
        _item.setID(_tmpID);
        final double _tmpQuantite;
        _tmpQuantite = _cursor.getDouble(_cursorIndexOfQuantite);
        _item.setQuantite(_tmpQuantite);
        final String _tmpCodeBarres;
        _tmpCodeBarres = _cursor.getString(_cursorIndexOfCodeBarres);
        _item.setCodeBarres(_tmpCodeBarres);
        final int _tmpNumeroJour;
        _tmpNumeroJour = _cursor.getInt(_cursorIndexOfNumeroJour);
        _item.setNumeroJour(_tmpNumeroJour);
        final int _tmpNumeroSemaine;
        _tmpNumeroSemaine = _cursor.getInt(_cursorIndexOfNumeroSemaine);
        _item.setNumeroSemaine(_tmpNumeroSemaine);
        final int _tmpNumeroMois;
        _tmpNumeroMois = _cursor.getInt(_cursorIndexOfNumeroMois);
        _item.setNumeroMois(_tmpNumeroMois);
        final String _tmpNature;
        _tmpNature = _cursor.getString(_cursorIndexOfNature);
        _item.setNature(_tmpNature);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<String> getCodeBarres(final String sNature) {
    final String _sql = "SELECT code_barres FROM `table` WHERE nature = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (sNature == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, sNature);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final List<String> _result = new ArrayList<String>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final String _item;
        _item = _cursor.getString(0);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<ConsoData> getConsoJour(final String sNature, final int sNumeroJour) {
    final String _sql = "SELECT * FROM `table` WHERE nature = ? AND numero_jour = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (sNature == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, sNature);
    }
    _argIndex = 2;
    _statement.bindLong(_argIndex, sNumeroJour);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfID = CursorUtil.getColumnIndexOrThrow(_cursor, "ID");
      final int _cursorIndexOfQuantite = CursorUtil.getColumnIndexOrThrow(_cursor, "quantite");
      final int _cursorIndexOfCodeBarres = CursorUtil.getColumnIndexOrThrow(_cursor, "code_barres");
      final int _cursorIndexOfNumeroJour = CursorUtil.getColumnIndexOrThrow(_cursor, "numero_jour");
      final int _cursorIndexOfNumeroSemaine = CursorUtil.getColumnIndexOrThrow(_cursor, "numero_semaine");
      final int _cursorIndexOfNumeroMois = CursorUtil.getColumnIndexOrThrow(_cursor, "numero_mois");
      final int _cursorIndexOfNature = CursorUtil.getColumnIndexOrThrow(_cursor, "nature");
      final List<ConsoData> _result = new ArrayList<ConsoData>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ConsoData _item;
        _item = new ConsoData();
        final int _tmpID;
        _tmpID = _cursor.getInt(_cursorIndexOfID);
        _item.setID(_tmpID);
        final double _tmpQuantite;
        _tmpQuantite = _cursor.getDouble(_cursorIndexOfQuantite);
        _item.setQuantite(_tmpQuantite);
        final String _tmpCodeBarres;
        _tmpCodeBarres = _cursor.getString(_cursorIndexOfCodeBarres);
        _item.setCodeBarres(_tmpCodeBarres);
        final int _tmpNumeroJour;
        _tmpNumeroJour = _cursor.getInt(_cursorIndexOfNumeroJour);
        _item.setNumeroJour(_tmpNumeroJour);
        final int _tmpNumeroSemaine;
        _tmpNumeroSemaine = _cursor.getInt(_cursorIndexOfNumeroSemaine);
        _item.setNumeroSemaine(_tmpNumeroSemaine);
        final int _tmpNumeroMois;
        _tmpNumeroMois = _cursor.getInt(_cursorIndexOfNumeroMois);
        _item.setNumeroMois(_tmpNumeroMois);
        final String _tmpNature;
        _tmpNature = _cursor.getString(_cursorIndexOfNature);
        _item.setNature(_tmpNature);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<ConsoData> getConsoSemaine(final String sNature, final int sNumeroSemaine) {
    final String _sql = "SELECT * FROM `table` WHERE nature = ? AND numero_semaine = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (sNature == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, sNature);
    }
    _argIndex = 2;
    _statement.bindLong(_argIndex, sNumeroSemaine);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfID = CursorUtil.getColumnIndexOrThrow(_cursor, "ID");
      final int _cursorIndexOfQuantite = CursorUtil.getColumnIndexOrThrow(_cursor, "quantite");
      final int _cursorIndexOfCodeBarres = CursorUtil.getColumnIndexOrThrow(_cursor, "code_barres");
      final int _cursorIndexOfNumeroJour = CursorUtil.getColumnIndexOrThrow(_cursor, "numero_jour");
      final int _cursorIndexOfNumeroSemaine = CursorUtil.getColumnIndexOrThrow(_cursor, "numero_semaine");
      final int _cursorIndexOfNumeroMois = CursorUtil.getColumnIndexOrThrow(_cursor, "numero_mois");
      final int _cursorIndexOfNature = CursorUtil.getColumnIndexOrThrow(_cursor, "nature");
      final List<ConsoData> _result = new ArrayList<ConsoData>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ConsoData _item;
        _item = new ConsoData();
        final int _tmpID;
        _tmpID = _cursor.getInt(_cursorIndexOfID);
        _item.setID(_tmpID);
        final double _tmpQuantite;
        _tmpQuantite = _cursor.getDouble(_cursorIndexOfQuantite);
        _item.setQuantite(_tmpQuantite);
        final String _tmpCodeBarres;
        _tmpCodeBarres = _cursor.getString(_cursorIndexOfCodeBarres);
        _item.setCodeBarres(_tmpCodeBarres);
        final int _tmpNumeroJour;
        _tmpNumeroJour = _cursor.getInt(_cursorIndexOfNumeroJour);
        _item.setNumeroJour(_tmpNumeroJour);
        final int _tmpNumeroSemaine;
        _tmpNumeroSemaine = _cursor.getInt(_cursorIndexOfNumeroSemaine);
        _item.setNumeroSemaine(_tmpNumeroSemaine);
        final int _tmpNumeroMois;
        _tmpNumeroMois = _cursor.getInt(_cursorIndexOfNumeroMois);
        _item.setNumeroMois(_tmpNumeroMois);
        final String _tmpNature;
        _tmpNature = _cursor.getString(_cursorIndexOfNature);
        _item.setNature(_tmpNature);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<ConsoData> getConsoMois(final String sNature, final int sNumeroMois) {
    final String _sql = "SELECT * FROM `table` WHERE nature = ? AND numero_mois = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (sNature == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, sNature);
    }
    _argIndex = 2;
    _statement.bindLong(_argIndex, sNumeroMois);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfID = CursorUtil.getColumnIndexOrThrow(_cursor, "ID");
      final int _cursorIndexOfQuantite = CursorUtil.getColumnIndexOrThrow(_cursor, "quantite");
      final int _cursorIndexOfCodeBarres = CursorUtil.getColumnIndexOrThrow(_cursor, "code_barres");
      final int _cursorIndexOfNumeroJour = CursorUtil.getColumnIndexOrThrow(_cursor, "numero_jour");
      final int _cursorIndexOfNumeroSemaine = CursorUtil.getColumnIndexOrThrow(_cursor, "numero_semaine");
      final int _cursorIndexOfNumeroMois = CursorUtil.getColumnIndexOrThrow(_cursor, "numero_mois");
      final int _cursorIndexOfNature = CursorUtil.getColumnIndexOrThrow(_cursor, "nature");
      final List<ConsoData> _result = new ArrayList<ConsoData>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ConsoData _item;
        _item = new ConsoData();
        final int _tmpID;
        _tmpID = _cursor.getInt(_cursorIndexOfID);
        _item.setID(_tmpID);
        final double _tmpQuantite;
        _tmpQuantite = _cursor.getDouble(_cursorIndexOfQuantite);
        _item.setQuantite(_tmpQuantite);
        final String _tmpCodeBarres;
        _tmpCodeBarres = _cursor.getString(_cursorIndexOfCodeBarres);
        _item.setCodeBarres(_tmpCodeBarres);
        final int _tmpNumeroJour;
        _tmpNumeroJour = _cursor.getInt(_cursorIndexOfNumeroJour);
        _item.setNumeroJour(_tmpNumeroJour);
        final int _tmpNumeroSemaine;
        _tmpNumeroSemaine = _cursor.getInt(_cursorIndexOfNumeroSemaine);
        _item.setNumeroSemaine(_tmpNumeroSemaine);
        final int _tmpNumeroMois;
        _tmpNumeroMois = _cursor.getInt(_cursorIndexOfNumeroMois);
        _item.setNumeroMois(_tmpNumeroMois);
        final String _tmpNature;
        _tmpNature = _cursor.getString(_cursorIndexOfNature);
        _item.setNature(_tmpNature);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
