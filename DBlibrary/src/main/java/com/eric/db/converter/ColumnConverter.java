package com.eric.db.converter;

import android.database.Cursor;

import com.eric.db.sqlite.ColumnDbType;


public interface ColumnConverter<T> {

    T getFieldValue(final Cursor cursor, int index);

    Object fieldValue2DbValue(T fieldValue);

    ColumnDbType getColumnDbType();
}
