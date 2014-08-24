package io.logbase.collections.impl;

import io.logbase.collections.BatchIterator;
import io.logbase.collections.BatchList;

import static com.google.common.base.Preconditions.*;

import java.util.*;

/**
 * Created by Kousik on 15/08/14
 */
public class BooleanList implements BatchList<Boolean> {
    private int arrayIndex = 0;
    boolean isClosed = false;
    BitSet bits = new BitSet();
    boolean holder[] = new boolean[1];

    /*
     * Iterate the given array from offset till length and insert
     * entries into the BitSet
     */
    public void write(boolean[] values, int offset, int length) {
        int start = offset;
        int end = offset + length;

        for (; start < end; start++) {
            if (values[start] == true) {
                bits.set(arrayIndex);
            }
            arrayIndex++;
        }
    }

    @Override
    public void add(Boolean value) {
        checkNotNull(value, "Null vales are not permitted");
        checkState(!isClosed, "Attempting to modify a closed list.");
        holder[0] = value.booleanValue();
        write(holder, 0, 1);
    }

    @Override
    public void addPrimitiveArray(Object values, int offset, int length) {
        checkNotNull(values, "Null vales are not permitted");
        checkArgument(values instanceof boolean[], "values must be boolean[], found " + values.getClass().getSimpleName());
        checkState(!isClosed, "Attempting to modify a closed list.");
        write((boolean[]) values, offset, length);
    }

    public void writeAll(BatchIterator<Boolean> source) {
        if (source.primitiveTypeSupport()) {
            boolean[] temp_holder = new boolean[1024];
            int count;
            while (source.hasNext()) {
                count = source.readNative(temp_holder, 0, temp_holder.length);
                if (count > 0) {
                    write(temp_holder, 0, count);
                }
            }
        } else {
            while (source.hasNext()) {
                add(source.next());
            }
        }
    }

  @Override
  public long size() {
    return arrayIndex;
  }

  @Override
    public boolean primitiveTypeSupport() {
        return true;
    }

    @Override
    public BatchIterator<Boolean> batchIterator(long maxIndex) {
        return new BooleanListIterator(bits, arrayIndex);
    }

    @Override
    public boolean close() {
        isClosed = true;
        return isClosed;
    }

    @Override
    public void addAll(BatchIterator<Boolean> iterator) {
        boolean[] buffer = new boolean[1024];
        int count;
        if (iterator.primitiveTypeSupport()) {
            while (iterator.hasNext()) {
                count = iterator.readNative(buffer, 0, buffer.length);
                if (count > 0) {
                    break;
                }
                write(buffer, 0, count);
            }
        } else {
            while (iterator.hasNext()) {
                for (count = 0;
                     count < buffer.length && iterator.hasNext();
                     count++) {
                    buffer[count] = iterator.next();
                }
                write(buffer, 0, count);
            }
        }
    }
}
