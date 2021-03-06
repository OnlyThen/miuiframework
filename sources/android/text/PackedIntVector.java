package android.text;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.annotations.VisibleForTesting.Visibility;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;

@VisibleForTesting(visibility = Visibility.PACKAGE)
public class PackedIntVector {
    private final int mColumns;
    private int mRowGapLength = this.mRows;
    private int mRowGapStart = 0;
    private int mRows = 0;
    private int[] mValueGap;
    private int[] mValues = null;

    public PackedIntVector(int columns) {
        this.mColumns = columns;
        this.mValueGap = new int[(columns * 2)];
    }

    public int getValue(int row, int column) {
        int columns = this.mColumns;
        if ((row | column) < 0 || row >= size() || column >= columns) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(row);
            stringBuilder.append(", ");
            stringBuilder.append(column);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        if (row >= this.mRowGapStart) {
            row += this.mRowGapLength;
        }
        int value = this.mValues[(row * columns) + column];
        int[] valuegap = this.mValueGap;
        if (row >= valuegap[column]) {
            return value + valuegap[column + columns];
        }
        return value;
    }

    public void setValue(int row, int column, int value) {
        if ((row | column) < 0 || row >= size() || column >= this.mColumns) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(row);
            stringBuilder.append(", ");
            stringBuilder.append(column);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        if (row >= this.mRowGapStart) {
            row += this.mRowGapLength;
        }
        int[] valuegap = this.mValueGap;
        if (row >= valuegap[column]) {
            value -= valuegap[this.mColumns + column];
        }
        this.mValues[(this.mColumns * row) + column] = value;
    }

    private void setValueInternal(int row, int column, int value) {
        if (row >= this.mRowGapStart) {
            row += this.mRowGapLength;
        }
        int[] valuegap = this.mValueGap;
        if (row >= valuegap[column]) {
            value -= valuegap[this.mColumns + column];
        }
        this.mValues[(this.mColumns * row) + column] = value;
    }

    public void adjustValuesBelow(int startRow, int column, int delta) {
        if ((startRow | column) < 0 || startRow > size() || column >= width()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(startRow);
            stringBuilder.append(", ");
            stringBuilder.append(column);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        if (startRow >= this.mRowGapStart) {
            startRow += this.mRowGapLength;
        }
        moveValueGapTo(column, startRow);
        int[] iArr = this.mValueGap;
        int i = this.mColumns + column;
        iArr[i] = iArr[i] + delta;
    }

    public void insertAt(int row, int[] values) {
        StringBuilder stringBuilder;
        if (row < 0 || row > size()) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("row ");
            stringBuilder.append(row);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        } else if (values == null || values.length >= width()) {
            moveRowGapTo(row);
            if (this.mRowGapLength == 0) {
                growBuffer();
            }
            this.mRowGapStart++;
            this.mRowGapLength--;
            int i;
            if (values == null) {
                for (i = this.mColumns - 1; i >= 0; i--) {
                    setValueInternal(row, i, 0);
                }
                return;
            }
            for (i = this.mColumns - 1; i >= 0; i--) {
                setValueInternal(row, i, values[i]);
            }
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append("value count ");
            stringBuilder.append(values.length);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
    }

    public void deleteAt(int row, int count) {
        if ((row | count) < 0 || row + count > size()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(row);
            stringBuilder.append(", ");
            stringBuilder.append(count);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        moveRowGapTo(row + count);
        this.mRowGapStart -= count;
        this.mRowGapLength += count;
    }

    public int size() {
        return this.mRows - this.mRowGapLength;
    }

    public int width() {
        return this.mColumns;
    }

    private final void growBuffer() {
        int columns = this.mColumns;
        int[] newvalues = ArrayUtils.newUnpaddedIntArray(GrowingArrayUtils.growSize(size()) * columns);
        int newsize = newvalues.length / columns;
        int[] valuegap = this.mValueGap;
        int rowgapstart = this.mRowGapStart;
        int after = this.mRows - (this.mRowGapLength + rowgapstart);
        int[] iArr = this.mValues;
        if (iArr != null) {
            System.arraycopy(iArr, 0, newvalues, 0, columns * rowgapstart);
            System.arraycopy(this.mValues, (this.mRows - after) * columns, newvalues, (newsize - after) * columns, after * columns);
        }
        for (int i = 0; i < columns; i++) {
            if (valuegap[i] >= rowgapstart) {
                valuegap[i] = valuegap[i] + (newsize - this.mRows);
                if (valuegap[i] < rowgapstart) {
                    valuegap[i] = rowgapstart;
                }
            }
        }
        this.mRowGapLength += newsize - this.mRows;
        this.mRows = newsize;
        this.mValues = newvalues;
    }

    private final void moveValueGapTo(int column, int where) {
        int[] valuegap = this.mValueGap;
        int[] values = this.mValues;
        int columns = this.mColumns;
        if (where != valuegap[column]) {
            int i;
            int i2;
            if (where > valuegap[column]) {
                for (i = valuegap[column]; i < where; i++) {
                    i2 = (i * columns) + column;
                    values[i2] = values[i2] + valuegap[column + columns];
                }
            } else {
                for (i = where; i < valuegap[column]; i++) {
                    i2 = (i * columns) + column;
                    values[i2] = values[i2] - valuegap[column + columns];
                }
            }
            valuegap[column] = where;
        }
    }

    private final void moveRowGapTo(int where) {
        int gapend = this.mRowGapStart;
        if (where != gapend) {
            int i;
            int moving;
            int[] valuegap;
            int destrow;
            int j;
            int val;
            if (where > gapend) {
                i = this.mRowGapLength;
                moving = (where + i) - (gapend + i);
                int columns = this.mColumns;
                valuegap = this.mValueGap;
                int[] values = this.mValues;
                gapend += i;
                for (i = gapend; i < gapend + moving; i++) {
                    destrow = (i - gapend) + this.mRowGapStart;
                    for (j = 0; j < columns; j++) {
                        val = values[(i * columns) + j];
                        if (i >= valuegap[j]) {
                            val += valuegap[j + columns];
                        }
                        if (destrow >= valuegap[j]) {
                            val -= valuegap[j + columns];
                        }
                        values[(destrow * columns) + j] = val;
                    }
                }
            } else {
                i = gapend - where;
                moving = this.mColumns;
                int[] valuegap2 = this.mValueGap;
                valuegap = this.mValues;
                gapend += this.mRowGapLength;
                for (int i2 = (where + i) - 1; i2 >= where; i2--) {
                    destrow = ((i2 - where) + gapend) - i;
                    for (j = 0; j < moving; j++) {
                        val = valuegap[(i2 * moving) + j];
                        if (i2 >= valuegap2[j]) {
                            val += valuegap2[j + moving];
                        }
                        if (destrow >= valuegap2[j]) {
                            val -= valuegap2[j + moving];
                        }
                        valuegap[(destrow * moving) + j] = val;
                    }
                }
            }
            this.mRowGapStart = where;
        }
    }
}
