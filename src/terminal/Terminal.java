package terminal;

/**
 * Emulates a terminal output. Throws pixels directly to screen, uses the old
 * VGA font. Is a slap in the face of the gods of OOP. Bwahaha
 */

public class Terminal {

	public static final int UR = 191;
	public static final int LL = 192;
	public static final int LR = 217;
	public static final int UL = 218;
	public static final int VERT = 179;
	public static final int HOR = 196;

	private static int cellWidth = 8;
	private static int cellHeight = 12;

	public static int terminalWidth = 100;
	public static int terminalHeight = 50;

	private static int writeForeColor = 0xFFFFFF;
	private static int writeBackColor = 0x000000;

	public static int cursorX;
	public static int cursorY;

	private static SpriteSheet spriteSheet;

	private static int[] foreData;
	private static int[] backData;
	private static int[] charData;

	public static int offsetX = 0;
	public static int offsetY = 0;

	public static void initialize(int width, int height) {
		spriteSheet = new SpriteSheet("curses.png", 8, 12);

		terminalWidth = width;
		terminalHeight = height;

		foreData = new int[terminalWidth * terminalHeight];
		backData = new int[terminalWidth * terminalHeight];
		charData = new int[terminalWidth * terminalHeight];

		reset();
	}

	public static void reset() {
		for (int i = 0; i < foreData.length; i++) {
			foreData[i] = 0xFFFFFFFF;
			backData[i] = 0;
			charData[i] = 0;
		}
		setOffset(0,0);
	}

	public static void printData() {
		setCursor(0, 0);
		for (int i = 0; i < foreData.length; i++) {
			printChar(charData[i], foreData[i], backData[i]);
		}
	}

	/**
	 * Only called by printData(), don't use elsewhere
	 * @param id
	 * @param foreColor
	 * @param backColor
	 */
	public static void printChar(int id, int foreColor, int backColor) {
		int x = cursorX * cellWidth;
		int y = cursorY * cellHeight;
		spriteSheet.render(x, y, id, foreColor, backColor);
		cursorX++;
		if (cursorX == terminalWidth) {
			cursorX = 0;
			cursorY++;
		}
	}

	public static int pixelX(int x) {
		return (cellWidth * (x + offsetX));

	}

	public static int pixelY(int y) {
		return (cellHeight * (y + offsetY));
	}

	public static int pixelWidth() {
		return (terminalWidth * cellWidth);
	}

	public static int pixelHeight() {
		return (terminalHeight * cellHeight);
	}

	public static void setCursor(int x, int y) {
		cursorX = x;
		cursorY = y;
	}

	public static void writeString(String s, int x, int y) {
		if (s == null || s.length() == 0) return;
		for (int i = 0; i < s.length(); i++) {
			setForeColor(x + i, y, writeForeColor);
			writeCharacter(s.charAt(i), x + i, y);
		}
	}

	public static void writeColorString(ColorString s, int x, int y) {
		for (int i = 0; i < s.text.length(); i++) {
			writeCharacter(s.text.charAt(i), x + i, y);
			setForeColor(x + i, y, s.colorsFG[i]);
			if (s.bg) {
				setBackColor(x + i, y, s.colorsBG[i]);
			}
		}
	}

	public static void writeCharacter(int character, int x, int y) {
		int id = (y + offsetY) * terminalWidth + (x + offsetX);
		if (id < charData.length && id >= 0)
			charData[id] = character;
		else
			System.out.println("Attempted to set character out of bounds");
	}

	public static void setForeColor(int color) {
		writeForeColor = color;
	}

	public static void setForeColor(int x, int y, int color) {
		int id = (y + offsetY) * terminalWidth + (x + offsetX);
		if (id < foreData.length && id >= 0)
			foreData[id] = color;
		else
			System.out.println("Attempted to set fore color out of bounds");

	}

	public static void setBackColor(int color) {
		writeBackColor = color;
	}

	public static void setBackColor(int x, int y, int color) {
		int id = (y + offsetY) * terminalWidth + (x + offsetX);
		if (id < backData.length && id >= 0)
			backData[id] = color;
		else
			System.out.println("Attempted to set back color out of bounds");
	}

	public static void setOffset(int x, int y) {
		offsetX = x;
		offsetY = y;
	}
	
	public static void setOffsetX(int x) {
		offsetX = x;
	}

	public static void setOffsetY(int y) {
		offsetY = y;
	}

	public static void writeHorizontalLine(int y) {
		for (int x = 0; x < terminalWidth; x++) {
			setForeColor(x, y, writeForeColor);
			setBackColor(x, y, writeBackColor);
			writeCharacter(HOR, x, y);
		}
	}

	public static void writeRect(int x, int y, int width, int height) {

		setForeColor(x, y, writeForeColor);
		setBackColor(x, y, writeBackColor);
		writeCharacter(UL, x, y);

		setForeColor(x + width - 1, y, writeForeColor);
		setBackColor(x + width - 1, y, writeBackColor);
		writeCharacter(UR, x + width - 1, y);

		setForeColor(x, y + height - 1, writeForeColor);
		setBackColor(x, y + height - 1, writeBackColor);
		writeCharacter(LL, x, y + height - 1);

		setForeColor(x + width - 1, y + height - 1, writeForeColor);
		setBackColor(x + width - 1, y + height - 1, writeBackColor);
		writeCharacter(LR, x + width - 1, y + height - 1);

		for (int a = x + 1; a < x + width - 1; a++) {
			for (int b = y + 1; b < y + height - 1; b++) {
				writeCharacter(' ', a, b);
			}
		}

		for (int newX = x + 1; newX < x + width - 1; newX++) {
			setForeColor(newX, y, writeForeColor);
			setBackColor(newX, y, writeBackColor);
			writeCharacter(HOR, newX, y);

			setForeColor(newX, y + height - 1, writeForeColor);
			setBackColor(newX, y + height - 1, writeBackColor);
			writeCharacter(HOR, newX, y + height - 1);
		}

		for (int newY = y + 1; newY < y + height - 1; newY++) {
			setForeColor(x, newY, writeForeColor);
			setBackColor(x, newY, writeBackColor);
			writeCharacter(VERT, x, newY);

			setForeColor(x + width - 1, newY, writeForeColor);
			setBackColor(x + width - 1, newY, writeBackColor);
			writeCharacter(VERT, x + width - 1, newY);
		}
	}
}