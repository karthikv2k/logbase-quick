package io.logbase.collections.impl;

import io.logbase.collections.BatchIterator;

import java.util.BitSet;
import java.util.Iterator;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by Kousik on 15/08/14.
 */
public class BooleanListIterator implements BatchIterator<Boolean>{
    BitSet bits;
    private int max_size;
    private int arrayIndex;
    private boolean[] localBuf = new boolean[10*1024];
    private int localBufPos = 0;
    private int localBufSize = 0;

    BooleanListIterator(BitSet bits, int size) {
        this.bits = bits;
        this.max_size = size;
    }

    @Override
    public Iterator<Boolean> iterator() {
        return this;
    }

    private int readInternal(boolean[] out, int offset, int count){
        /*
         * return -1 if there are not more elements to read from list.
         */
        if(arrayIndex >= max_size){
            return -1;
        }

        int idx;
        for(idx = offset;
            arrayIndex < max_size && idx < offset+count;
            idx++, arrayIndex++){

            out[offset + idx] = bits.get(arrayIndex);
        }
        return idx - offset;
    }

    @Override
    public int read(Boolean[] buffer, int offset, int rows) {
        checkNotNull(buffer, "Null buffer is not permitted");
        checkArgument(offset+rows<=buffer.length, "length of buffer should be greater than offset+rows.");
        boolean[] holder = new boolean[buffer.length];
        int count = readInternal(holder, 0, rows);
        for(int idx=0; idx<holder.length && idx<count; idx++){
            buffer[offset+idx] = holder[idx];
        }
        return count;
    }

    @Override
    public boolean primitiveTypeSupport() {
        return true;
    }

    @Override
    public int readNative(Object buffer, int offset, int rows) throws ClassCastException {
        checkNotNull(buffer, "Null buffer is not permitted");
        checkArgument(buffer instanceof boolean[], "buffer must be boolean[], found " +
                buffer.getClass().getSimpleName());
        boolean[] nativeBuffer = (boolean[]) buffer;
        checkArgument(offset+rows<=nativeBuffer.length, "length of buffer should be greater than offset+rows.");
        return readInternal(nativeBuffer, offset, rows);
    }

    @Override
    public boolean hasNext() {
        return arrayIndex < max_size;
    }

    @Override
    public Boolean next() {
        if(localBufSize > 0 && localBufPos < localBufSize){
            return localBuf[localBufPos++];
        } else {
            localBufSize = readInternal(localBuf, 0, localBuf.length);
            localBufPos = 0;
            if(localBufSize>0){
                return next();
            } else {
                checkState(hasNext(), "check hasNext() before calling next()");
                return null;
            }
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Readonly list doesn't support remove().");
    }
}
