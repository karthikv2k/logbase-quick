package io.logbase.column.readonly;

import io.logbase.collections.BatchIterator;
import io.logbase.collections.BatchList;
import io.logbase.collections.impl.BooleanList;
import io.logbase.collections.impl.BooleanListOffHeap;
import io.logbase.collections.impl.OffHeapBitSet;
import io.logbase.column.Column;
import io.logbase.column.ColumnIterator;
import io.logbase.column.SimpleColumnIterator;

import java.util.BitSet;

/**
 * Created by Kousik on 23/08/14.
 */
public class BooleanColumn extends AbstractROColumn<Boolean> {
    private final BatchList values;

    /*
     * By default choose BooleanList which uses BitSet
     */
    public BooleanColumn(Column<Boolean> column){
        super(column);
        values = new BooleanList(column.getValuesIterator());
        values.addAll(column.getValuesIterator());
        values.close();
    }

    public BooleanColumn(Column<Boolean> column, Object obj) {
        super(column);
        if (obj.getClass().equals(OffHeapBitSet.class)) {
            values = new BooleanListOffHeap((int)column.getRowCount());
            values.addAll(column.getValuesIterator());
            values.close();
        } else {
            values = new BooleanList(column.getValuesIterator());
            values.addAll(column.getValuesIterator());
            values.close();
        }
    }

    @Override
    public Class getColumnType() {
        return Boolean.class;
    }

    @Override
    public long getValuesCount() {
        return values.size();
    }

    @Override
    public ColumnIterator<Object> getSimpleIterator(long maxRowNum) {
        return new SimpleColumnIterator(this, maxRowNum);
    }

    @Override
    public ColumnIterator<Object> getSimpleIterator() {
        return new SimpleColumnIterator(this, getRowCount());
    }

    @Override
    public BatchIterator<Boolean> getValuesIterator() {
        return values.batchIterator(values.size());
    }

}
