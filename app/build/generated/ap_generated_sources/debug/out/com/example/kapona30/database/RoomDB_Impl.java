package com.example.kapona30.database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class RoomDB_Impl extends RoomDB {
  private volatile ConsoDao _consoDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `table` (`ID` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `quantite` REAL NOT NULL, `code_barres` TEXT, `numero_jour` INTEGER NOT NULL, `numero_semaine` INTEGER NOT NULL, `numero_mois` INTEGER NOT NULL, `nature` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '3e53ecb6ee955bc37ed7e1851f13966b')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `table`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsTable = new HashMap<String, TableInfo.Column>(7);
        _columnsTable.put("ID", new TableInfo.Column("ID", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTable.put("quantite", new TableInfo.Column("quantite", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTable.put("code_barres", new TableInfo.Column("code_barres", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTable.put("numero_jour", new TableInfo.Column("numero_jour", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTable.put("numero_semaine", new TableInfo.Column("numero_semaine", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTable.put("numero_mois", new TableInfo.Column("numero_mois", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTable.put("nature", new TableInfo.Column("nature", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTable = new TableInfo("table", _columnsTable, _foreignKeysTable, _indicesTable);
        final TableInfo _existingTable = TableInfo.read(_db, "table");
        if (! _infoTable.equals(_existingTable)) {
          return new RoomOpenHelper.ValidationResult(false, "table(com.example.kapona30.database.ConsoData).\n"
                  + " Expected:\n" + _infoTable + "\n"
                  + " Found:\n" + _existingTable);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "3e53ecb6ee955bc37ed7e1851f13966b", "ca639a2c9d11c471cb529a46430beef9");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public ConsoDao consoDao() {
    if (_consoDao != null) {
      return _consoDao;
    } else {
      synchronized(this) {
        if(_consoDao == null) {
          _consoDao = new ConsoDao_Impl(this);
        }
        return _consoDao;
      }
    }
  }
}
