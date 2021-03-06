package android.media;

import android.graphics.Color;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/* compiled from: Cea708CaptionRenderer */
class Cea708CCParser {
    public static final int CAPTION_EMIT_TYPE_BUFFER = 1;
    public static final int CAPTION_EMIT_TYPE_COMMAND_CLW = 4;
    public static final int CAPTION_EMIT_TYPE_COMMAND_CWX = 3;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DFX = 16;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DLC = 10;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DLW = 8;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DLY = 9;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DSW = 5;
    public static final int CAPTION_EMIT_TYPE_COMMAND_HDW = 6;
    public static final int CAPTION_EMIT_TYPE_COMMAND_RST = 11;
    public static final int CAPTION_EMIT_TYPE_COMMAND_SPA = 12;
    public static final int CAPTION_EMIT_TYPE_COMMAND_SPC = 13;
    public static final int CAPTION_EMIT_TYPE_COMMAND_SPL = 14;
    public static final int CAPTION_EMIT_TYPE_COMMAND_SWA = 15;
    public static final int CAPTION_EMIT_TYPE_COMMAND_TGW = 7;
    public static final int CAPTION_EMIT_TYPE_CONTROL = 2;
    private static final boolean DEBUG = false;
    private static final String MUSIC_NOTE_CHAR = new String("♫".getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    private static final String TAG = "Cea708CCParser";
    private final StringBuffer mBuffer = new StringBuffer();
    private int mCommand = 0;
    private DisplayListener mListener = new DisplayListener() {
        public void emitEvent(CaptionEvent event) {
        }
    };

    /* compiled from: Cea708CaptionRenderer */
    interface DisplayListener {
        void emitEvent(CaptionEvent captionEvent);
    }

    /* compiled from: Cea708CaptionRenderer */
    public static class CaptionColor {
        private static final int[] COLOR_MAP = new int[]{0, 15, 240, 255};
        public static final int OPACITY_FLASH = 1;
        private static final int[] OPACITY_MAP = new int[]{255, 254, 128, 0};
        public static final int OPACITY_SOLID = 0;
        public static final int OPACITY_TRANSLUCENT = 2;
        public static final int OPACITY_TRANSPARENT = 3;
        public final int blue;
        public final int green;
        public final int opacity;
        public final int red;

        public CaptionColor(int opacity, int red, int green, int blue) {
            this.opacity = opacity;
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        public int getArgbValue() {
            int i = OPACITY_MAP[this.opacity];
            int[] iArr = COLOR_MAP;
            return Color.argb(i, iArr[this.red], iArr[this.green], iArr[this.blue]);
        }
    }

    /* compiled from: Cea708CaptionRenderer */
    public static class CaptionEvent {
        public final Object obj;
        public final int type;

        public CaptionEvent(int type, Object obj) {
            this.type = type;
            this.obj = obj;
        }
    }

    /* compiled from: Cea708CaptionRenderer */
    public static class CaptionPenAttr {
        public static final int OFFSET_NORMAL = 1;
        public static final int OFFSET_SUBSCRIPT = 0;
        public static final int OFFSET_SUPERSCRIPT = 2;
        public static final int PEN_SIZE_LARGE = 2;
        public static final int PEN_SIZE_SMALL = 0;
        public static final int PEN_SIZE_STANDARD = 1;
        public final int edgeType;
        public final int fontTag;
        public final boolean italic;
        public final int penOffset;
        public final int penSize;
        public final int textTag;
        public final boolean underline;

        public CaptionPenAttr(int penSize, int penOffset, int textTag, int fontTag, int edgeType, boolean underline, boolean italic) {
            this.penSize = penSize;
            this.penOffset = penOffset;
            this.textTag = textTag;
            this.fontTag = fontTag;
            this.edgeType = edgeType;
            this.underline = underline;
            this.italic = italic;
        }
    }

    /* compiled from: Cea708CaptionRenderer */
    public static class CaptionPenColor {
        public final CaptionColor backgroundColor;
        public final CaptionColor edgeColor;
        public final CaptionColor foregroundColor;

        public CaptionPenColor(CaptionColor foregroundColor, CaptionColor backgroundColor, CaptionColor edgeColor) {
            this.foregroundColor = foregroundColor;
            this.backgroundColor = backgroundColor;
            this.edgeColor = edgeColor;
        }
    }

    /* compiled from: Cea708CaptionRenderer */
    public static class CaptionPenLocation {
        public final int column;
        public final int row;

        public CaptionPenLocation(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    /* compiled from: Cea708CaptionRenderer */
    public static class CaptionWindow {
        public final int anchorHorizontal;
        public final int anchorId;
        public final int anchorVertical;
        public final int columnCount;
        public final boolean columnLock;
        public final int id;
        public final int penStyle;
        public final int priority;
        public final boolean relativePositioning;
        public final int rowCount;
        public final boolean rowLock;
        public final boolean visible;
        public final int windowStyle;

        public CaptionWindow(int id, boolean visible, boolean rowLock, boolean columnLock, int priority, boolean relativePositioning, int anchorVertical, int anchorHorizontal, int anchorId, int rowCount, int columnCount, int penStyle, int windowStyle) {
            this.id = id;
            this.visible = visible;
            this.rowLock = rowLock;
            this.columnLock = columnLock;
            this.priority = priority;
            this.relativePositioning = relativePositioning;
            this.anchorVertical = anchorVertical;
            this.anchorHorizontal = anchorHorizontal;
            this.anchorId = anchorId;
            this.rowCount = rowCount;
            this.columnCount = columnCount;
            this.penStyle = penStyle;
            this.windowStyle = windowStyle;
        }
    }

    /* compiled from: Cea708CaptionRenderer */
    public static class CaptionWindowAttr {
        public final CaptionColor borderColor;
        public final int borderType;
        public final int displayEffect;
        public final int effectDirection;
        public final int effectSpeed;
        public final CaptionColor fillColor;
        public final int justify;
        public final int printDirection;
        public final int scrollDirection;
        public final boolean wordWrap;

        public CaptionWindowAttr(CaptionColor fillColor, CaptionColor borderColor, int borderType, boolean wordWrap, int printDirection, int scrollDirection, int justify, int effectDirection, int effectSpeed, int displayEffect) {
            this.fillColor = fillColor;
            this.borderColor = borderColor;
            this.borderType = borderType;
            this.wordWrap = wordWrap;
            this.printDirection = printDirection;
            this.scrollDirection = scrollDirection;
            this.justify = justify;
            this.effectDirection = effectDirection;
            this.effectSpeed = effectSpeed;
            this.displayEffect = displayEffect;
        }
    }

    /* compiled from: Cea708CaptionRenderer */
    private static class Const {
        public static final int CODE_C0_BS = 8;
        public static final int CODE_C0_CR = 13;
        public static final int CODE_C0_ETX = 3;
        public static final int CODE_C0_EXT1 = 16;
        public static final int CODE_C0_FF = 12;
        public static final int CODE_C0_HCR = 14;
        public static final int CODE_C0_NUL = 0;
        public static final int CODE_C0_P16 = 24;
        public static final int CODE_C0_RANGE_END = 31;
        public static final int CODE_C0_RANGE_START = 0;
        public static final int CODE_C0_SKIP1_RANGE_END = 23;
        public static final int CODE_C0_SKIP1_RANGE_START = 16;
        public static final int CODE_C0_SKIP2_RANGE_END = 31;
        public static final int CODE_C0_SKIP2_RANGE_START = 24;
        public static final int CODE_C1_CLW = 136;
        public static final int CODE_C1_CW0 = 128;
        public static final int CODE_C1_CW1 = 129;
        public static final int CODE_C1_CW2 = 130;
        public static final int CODE_C1_CW3 = 131;
        public static final int CODE_C1_CW4 = 132;
        public static final int CODE_C1_CW5 = 133;
        public static final int CODE_C1_CW6 = 134;
        public static final int CODE_C1_CW7 = 135;
        public static final int CODE_C1_DF0 = 152;
        public static final int CODE_C1_DF1 = 153;
        public static final int CODE_C1_DF2 = 154;
        public static final int CODE_C1_DF3 = 155;
        public static final int CODE_C1_DF4 = 156;
        public static final int CODE_C1_DF5 = 157;
        public static final int CODE_C1_DF6 = 158;
        public static final int CODE_C1_DF7 = 159;
        public static final int CODE_C1_DLC = 142;
        public static final int CODE_C1_DLW = 140;
        public static final int CODE_C1_DLY = 141;
        public static final int CODE_C1_DSW = 137;
        public static final int CODE_C1_HDW = 138;
        public static final int CODE_C1_RANGE_END = 159;
        public static final int CODE_C1_RANGE_START = 128;
        public static final int CODE_C1_RST = 143;
        public static final int CODE_C1_SPA = 144;
        public static final int CODE_C1_SPC = 145;
        public static final int CODE_C1_SPL = 146;
        public static final int CODE_C1_SWA = 151;
        public static final int CODE_C1_TGW = 139;
        public static final int CODE_C2_RANGE_END = 31;
        public static final int CODE_C2_RANGE_START = 0;
        public static final int CODE_C2_SKIP0_RANGE_END = 7;
        public static final int CODE_C2_SKIP0_RANGE_START = 0;
        public static final int CODE_C2_SKIP1_RANGE_END = 15;
        public static final int CODE_C2_SKIP1_RANGE_START = 8;
        public static final int CODE_C2_SKIP2_RANGE_END = 23;
        public static final int CODE_C2_SKIP2_RANGE_START = 16;
        public static final int CODE_C2_SKIP3_RANGE_END = 31;
        public static final int CODE_C2_SKIP3_RANGE_START = 24;
        public static final int CODE_C3_RANGE_END = 159;
        public static final int CODE_C3_RANGE_START = 128;
        public static final int CODE_C3_SKIP4_RANGE_END = 135;
        public static final int CODE_C3_SKIP4_RANGE_START = 128;
        public static final int CODE_C3_SKIP5_RANGE_END = 143;
        public static final int CODE_C3_SKIP5_RANGE_START = 136;
        public static final int CODE_G0_MUSICNOTE = 127;
        public static final int CODE_G0_RANGE_END = 127;
        public static final int CODE_G0_RANGE_START = 32;
        public static final int CODE_G1_RANGE_END = 255;
        public static final int CODE_G1_RANGE_START = 160;
        public static final int CODE_G2_BLK = 48;
        public static final int CODE_G2_NBTSP = 33;
        public static final int CODE_G2_RANGE_END = 127;
        public static final int CODE_G2_RANGE_START = 32;
        public static final int CODE_G2_TSP = 32;
        public static final int CODE_G3_CC = 160;
        public static final int CODE_G3_RANGE_END = 255;
        public static final int CODE_G3_RANGE_START = 160;

        private Const() {
        }
    }

    Cea708CCParser(DisplayListener listener) {
        if (listener != null) {
            this.mListener = listener;
        }
    }

    private void emitCaptionEvent(CaptionEvent captionEvent) {
        emitCaptionBuffer();
        this.mListener.emitEvent(captionEvent);
    }

    private void emitCaptionBuffer() {
        if (this.mBuffer.length() > 0) {
            this.mListener.emitEvent(new CaptionEvent(1, this.mBuffer.toString()));
            this.mBuffer.setLength(0);
        }
    }

    public void parse(byte[] data) {
        int pos = 0;
        while (pos < data.length) {
            pos = parseServiceBlockData(data, pos);
        }
        emitCaptionBuffer();
    }

    private int parseServiceBlockData(byte[] data, int pos) {
        this.mCommand = data[pos] & 255;
        pos++;
        int i = this.mCommand;
        if (i == 16) {
            return parseExt1(data, pos);
        }
        if (i >= 0 && i <= 31) {
            return parseC0(data, pos);
        }
        i = this.mCommand;
        if (i >= 128 && i <= 159) {
            return parseC1(data, pos);
        }
        i = this.mCommand;
        if (i >= 32 && i <= 127) {
            return parseG0(data, pos);
        }
        i = this.mCommand;
        if (i < 160 || i > 255) {
            return pos;
        }
        return parseG1(data, pos);
    }

    private int parseC0(byte[] data, int pos) {
        int i = this.mCommand;
        if (i < 24 || i > 31) {
            i = this.mCommand;
            if (i >= 16 && i <= 23) {
                return pos + 1;
            }
            i = this.mCommand;
            if (i == 0) {
                return pos;
            }
            if (i == 3) {
                emitCaptionEvent(new CaptionEvent(2, Character.valueOf((char) i)));
                return pos;
            } else if (i != 8) {
                switch (i) {
                    case 12:
                        emitCaptionEvent(new CaptionEvent(2, Character.valueOf((char) i)));
                        return pos;
                    case 13:
                        this.mBuffer.append(10);
                        return pos;
                    case 14:
                        emitCaptionEvent(new CaptionEvent(2, Character.valueOf((char) i)));
                        return pos;
                    default:
                        return pos;
                }
            } else {
                emitCaptionEvent(new CaptionEvent(2, Character.valueOf((char) i)));
                return pos;
            }
        }
        if (i == 24) {
            try {
                if (data[pos] == (byte) 0) {
                    this.mBuffer.append((char) data[pos + 1]);
                } else {
                    this.mBuffer.append(new String(Arrays.copyOfRange(data, pos, pos + 2), "EUC-KR"));
                }
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, "P16 Code - Could not find supported encoding", e);
            }
        }
        return pos + 2;
    }

    private int parseC1(byte[] data, int pos) {
        int thisR;
        int windowId = this.mCommand;
        int pos2;
        int pos3;
        int pos4;
        switch (windowId) {
            case 128:
            case 129:
            case 130:
            case 131:
            case 132:
            case 133:
            case 134:
            case 135:
                emitCaptionEvent(new CaptionEvent(3, Integer.valueOf(windowId - 128)));
                break;
            case 136:
                windowId = pos + 1;
                emitCaptionEvent(new CaptionEvent(4, Integer.valueOf(data[pos] & 255)));
                return windowId;
            case 137:
                windowId = pos + 1;
                emitCaptionEvent(new CaptionEvent(5, Integer.valueOf(data[pos] & 255)));
                return windowId;
            case 138:
                windowId = pos + 1;
                emitCaptionEvent(new CaptionEvent(6, Integer.valueOf(data[pos] & 255)));
                return windowId;
            case 139:
                windowId = pos + 1;
                emitCaptionEvent(new CaptionEvent(7, Integer.valueOf(data[pos] & 255)));
                return windowId;
            case 140:
                windowId = pos + 1;
                emitCaptionEvent(new CaptionEvent(8, Integer.valueOf(data[pos] & 255)));
                return windowId;
            case 141:
                windowId = pos + 1;
                emitCaptionEvent(new CaptionEvent(9, Integer.valueOf(data[pos] & 255)));
                return windowId;
            case 142:
                emitCaptionEvent(new CaptionEvent(10, null));
                break;
            case 143:
                emitCaptionEvent(new CaptionEvent(11, null));
                break;
            case 144:
                pos2 = pos + 2;
                emitCaptionEvent(new CaptionEvent(12, new CaptionPenAttr(data[pos] & 3, (data[pos] & 12) >> 2, (data[pos] & 240) >> 4, 7 & data[pos + 1], (data[pos + 1] & 56) >> 3, (data[pos + 1] & 64) != 0, (data[pos + 1] & 128) != 0)));
                return pos2;
            case 145:
                CaptionColor foregroundColor = new CaptionColor((data[pos] & 192) >> 6, (data[pos] & 48) >> 4, (data[pos] & 12) >> 2, data[pos] & 3);
                int pos5 = pos + 1;
                CaptionColor backgroundColor = new CaptionColor((data[pos5] & 192) >> 6, (data[pos5] & 48) >> 4, (data[pos5] & 12) >> 2, data[pos5] & 3);
                pos5++;
                CaptionColor edgeColor = new CaptionColor(0, (data[pos5] & 48) >> 4, (12 & data[pos5]) >> 2, data[pos5] & 3);
                pos3 = pos5 + 1;
                emitCaptionEvent(new CaptionEvent(13, new CaptionPenColor(foregroundColor, backgroundColor, edgeColor)));
                return pos3;
            case 146:
                pos4 = pos + 2;
                emitCaptionEvent(new CaptionEvent(14, new CaptionPenLocation(data[pos] & 15, data[pos + 1] & 63)));
                return pos4;
            case 151:
                thisR = (data[pos] & 192) >> 6;
                int pos6 = pos + 4;
                emitCaptionEvent(new CaptionEvent(15, new CaptionWindowAttr(new CaptionColor(thisR, (data[pos] & 48) >> 4, (data[pos] & 12) >> 2, data[pos] & 3), new CaptionColor(0, (data[pos + 1] & 48) >> 4, (data[pos + 1] & 12) >> 2, data[pos + 1] & 3), ((data[pos + 2] & 128) >> 5) | ((data[pos + 1] & 192) >> 6), (data[pos + 2] & 64) != 0, (data[pos + 2] & 48) >> 4, (data[pos + 2] & 12) >> 2, data[pos + 2] & 3, (12 & data[pos + 3]) >> 2, (data[pos + 3] & 240) >> 4, 3 & data[pos + 3])));
                return pos6;
            case 152:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
                windowId -= 152;
                boolean visible = (data[pos] & 32) != 0;
                boolean rowLock = (data[pos] & 16) != 0;
                boolean columnLock = (data[pos] & 8) != 0;
                int priority = data[pos] & 7;
                boolean relativePositioning = (data[pos + 1] & 128) != 0;
                pos2 = data[pos + 1] & 127;
                int anchorHorizontal = data[pos + 2] & 255;
                pos3 = (data[pos + 3] & 240) >> 4;
                pos4 = 15 & data[pos + 3];
                int columnCount = data[pos + 4] & 63;
                int windowStyle = (data[pos + 5] & 56) >> 3;
                int penStyle = 7 & data[pos + 5];
                pos += 6;
                thisR = 16;
                emitCaptionEvent(new CaptionEvent(16, new CaptionWindow(windowId, visible, rowLock, columnLock, priority, relativePositioning, pos2, anchorHorizontal, pos3, pos4, columnCount, penStyle, windowStyle)));
                return pos;
            default:
                break;
        }
        return pos;
    }

    private int parseG0(byte[] data, int pos) {
        int i = this.mCommand;
        if (i == 127) {
            this.mBuffer.append(MUSIC_NOTE_CHAR);
        } else {
            this.mBuffer.append((char) i);
        }
        return pos;
    }

    private int parseG1(byte[] data, int pos) {
        this.mBuffer.append((char) this.mCommand);
        return pos;
    }

    private int parseExt1(byte[] data, int pos) {
        this.mCommand = data[pos] & 255;
        pos++;
        int i = this.mCommand;
        if (i >= 0 && i <= 31) {
            return parseC2(data, pos);
        }
        i = this.mCommand;
        if (i >= 128 && i <= 159) {
            return parseC3(data, pos);
        }
        i = this.mCommand;
        if (i >= 32 && i <= 127) {
            return parseG2(data, pos);
        }
        i = this.mCommand;
        if (i < 160 || i > 255) {
            return pos;
        }
        return parseG3(data, pos);
    }

    private int parseC2(byte[] data, int pos) {
        int i = this.mCommand;
        if (i >= 0 && i <= 7) {
            return pos;
        }
        i = this.mCommand;
        if (i >= 8 && i <= 15) {
            return pos + 1;
        }
        i = this.mCommand;
        if (i >= 16 && i <= 23) {
            return pos + 2;
        }
        i = this.mCommand;
        if (i < 24 || i > 31) {
            return pos;
        }
        return pos + 3;
    }

    private int parseC3(byte[] data, int pos) {
        int i = this.mCommand;
        if (i >= 128 && i <= 135) {
            return pos + 4;
        }
        i = this.mCommand;
        if (i < 136 || i > 143) {
            return pos;
        }
        return pos + 5;
    }

    private int parseG2(byte[] data, int pos) {
        int i = this.mCommand;
        if (i == 32 || i == 33 || i != 48) {
        }
        return pos;
    }

    private int parseG3(byte[] data, int pos) {
        int i = this.mCommand;
        return pos;
    }
}
