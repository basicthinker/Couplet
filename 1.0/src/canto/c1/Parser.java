package canto.c1;

import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

import canto.AbstractSyntaxTree;
import canto.Token;
import canto.c1.ast.*;
import canto.c1.exception.ParserException;

public class Parser implements canto.Parser {
	
	/** 非终极符编号常量 */
	private static final int PROGRAM = 0;
	private static final int BLOCK = 1;
	private static final int STMTS = 2;
	private static final int DECL = 3;
	private static final int STMT = 4;
	private static final int EXPR = 5;
	private static final int TYPE = 6;
	private static final int UN_OP = 7;
	private static final int BI_OP1 = 8;
	private static final int BI_OP2 = 9;
	private static final int BI_OP3 = 10;
	private static final int BI_OP4 = 11;
	
	/** 终极符编号常量 */
	private static final int END = 0;
	private static final int ID = 1;
	private static final int LITERAL = 2;
	private static final int IF = 3;
	private static final int ELSE = 4;
	private static final int WHILE = 5;
	private static final int INPUT = 6;
	private static final int OUTPUT = 7;
	private static final int INT = 8;
	private static final int L_BRACE = 9;
	private static final int R_BRACE = 10;
	private static final int L_PARENT = 11;
	private static final int R_PARENT  = 12;
	private static final int SEMI = 13;
	private static final int EQUALS = 14;
	private static final int NOT = 15;
	private static final int PLUS = 16;
	private static final int MINUS = 17;
	private static final int TIMES = 18;
	private static final int LESS = 19;
	private static final int LESS_EQUALS = 20;
	private static final int GREATER = 21;
	private static final int GREATER_EQUALS = 22;
	private static final int EQUALS_EQUALS = 23;
	private static final int NOT_EQUALS = 24;
	
	
	/** Goto表 */
	private static int[][] gotoTable = {
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态0 不使用
	    {2,  3,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态1
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态2
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态3
	    {0,  0,  5,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态4
	    {0,  12, 0,  7,  8,  0,  9,  0,  0,  0,  0,  0},  // 状态5
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态6
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态7
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态8
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态9
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态10
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态11
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态12
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态13
	    {0,  0,  0,  0,  0,  15, 0,  22, 0,  0,  0,  0},  // 状态14
	    {0,  0,  0,  0,  0,  0,  0,  0,  24, 26, 28, 30}, // 状态15
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态16
	    {0,  0,  0,  0,  0,  18, 0,  22, 0,  0,  0,  0},  // 状态17
	    {0,  0,  0,  0,  0,  0,  0,  0,  24, 26, 28, 30}, // 状态18
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态19
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态20
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态21
	    {0,  0,  0,  0,  0,  23, 0,  22, 0,  0,  0,  0},  // 状态22
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态23
	    {0,  0,  0,  0,  0,  25, 0,  22, 0,  0,  0,  0},  // 状态24
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态25
	    {0,  0,  0,  0,  0,  27, 0,  22, 0,  0,  0,  0},  // 状态26
	    {0,  0,  0,  0,  0,  0,  0,  0,  24, 0,  0,  0},  // 状态27
	    {0,  0,  0,  0,  0,  29, 0,  22, 0,  0,  0,  0},  // 状态28
	    {0,  0,  0,  0,  0,  0,  0,  0,  24, 26, 0,  0},  // 状态29
	    {0,  0,  0,  0,  0,  31, 0,  22, 0,  0,  0,  0},  // 状态30
	    {0,  0,  0,  0,  0,  0,  0,  0,  24, 26, 28, 0},  // 状态31
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态32
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态33
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态34
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态35
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态36
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态37
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态38
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态39
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态40
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态41
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态42
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态43
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态44
	    {0,  0,  0,  0,  0,  46, 0,  22, 0,  0,  0,  0},  // 状态45
	    {0,  0,  0,  0,  0,  0,  0,  0,  24, 26, 28, 30}, // 状态46
	    {0,  12, 0,  0,  48, 0,  0,  0,  0,  0,  0,  0},  // 状态47
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态48
	    {0,  12, 0,  0,  50, 0,  0,  0,  0,  0,  0,  0},  // 状态49
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态50
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态51
	    {0,  0,  0,  0,  0,  53, 0,  22, 0,  0,  0,  0},  // 状态52
	    {0,  0,  0,  0,  0,  0,  0,  0,  24, 26, 28, 30}, // 状态53
	    {0,  12, 0,  0,  55, 0,  0,  0,  0,  0,  0,  0},  // 状态54
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态55
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态56
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态57
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态58
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态59
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态60
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态61
	    {0,  0,  0,  0,  0,  63, 0,  22, 0,  0,  0,  0},  // 状态62
	    {0,  0,  0,  0,  0,  0,  0,  0,  24, 26, 28, 30}, // 状态63
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态64
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态65
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态66
	};
	
	/** Action表 */
	private static int[][] actionTable = {
		{0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态0 不使用
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  4,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态1
	    {-1,0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态2
	    {-2,0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态3
	    {0, -6, 0,  -6, 0,  -6, -6, -6, -6, -6, -6, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态4
	    {0, 13, 0,  44, 0,  51, 56, 61, 66, 4,  6,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态5
	    {-3,-3, 0,  -3, -3, -3, -3, -3, -3, -3, -3, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态6
	    {0, -4, 0,  -4, -4, -4, -4, -4, -4, -4, -4, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态7
	    {0, -5, 0,  -5, -5, -5, -5, -5, -5, -5, -5, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态8
	    {0, 10, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态9
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  11, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态10
	    {0, -7, 0,  -7, -7, -7, -7, -7, -7, -7, -7, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态11
	    {0, -8, 0,  -8, -8, -8, -8, -8, -8, -8, -8, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态12
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  14, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态13
	    {0, 20, 21, 0,  0,  0,  0,  0,  0,  0,  0,  17, 0,  0,  0,  34, 32, 33, 0,  0,  0,  0,  0,  0,  0},  // 状态14
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  16, 0,  0,  36, 37, 35, 38, 39, 40, 41, 42, 43}, // 状态15
	    {0, -9, 0,  -9, -9, -9, -9, -9, -9, -9, -9, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态16
	    {0, 20, 21, 0,  0,  0,  0,  0,  0,  0,  0,  17, 0,  0,  0,  34, 32, 33, 0,  0,  0,  0,  0,  0,  0},  // 状态17
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  19, 0,  0,  0,  36, 37, 35, 38, 39, 40, 41, 42, 43}, // 状态18
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  -17,-17,0,  0,  -17,-17,-17,-17,-17,-17,-17,-17,-17},// 状态19
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  -15,-15,0,  0,  -15,-15,-15,-15,-15,-15,-15,-15,-15},// 状态20
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  -16,-16,0,  0,  -16,-16,-16,-16,-16,-16,-16,-16,-16},// 状态21
	    {0, 20, 21, 0,  0,  0,  0,  0,  0,  0,  0,  17, 0,  0,  0,  34, 32, 33, 0,  0,  0,  0,  0,  0,  0},  // 状态22
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  -18,-18,0,  0,  -18,-18,-18,-18,-18,-18,-18,-18,-18},// 状态23
	    {0, 20, 21, 0,  0,  0,  0,  0,  0,  0,  0,  17, 0,  0,  0,  34, 32, 33, 0,  0,  0,  0,  0,  0,  0},  // 状态24
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  -19,-19,0,  0,  -19,-19,-19,-19,-19,-19,-19,-19,-19},// 状态25
	    {0, 20, 21, 0,  0,  0,  0,  0,  0,  0,  0,  17, 0,  0,  0,  34, 32, 33, 0,  0,  0,  0,  0,  0,  0},  // 状态26
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  -20,-20,0,  0,  -20,-20,35, -20,-20,-20,-20,-20,-20},// 状态27
	    {0, 20, 21, 0,  0,  0,  0,  0,  0,  0,  0,  17, 0,  0,  0,  34, 32, 33, 0,  0,  0,  0,  0,  0,  0},  // 状态28
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  -21,-21,0,  0,  32, 33, 35, -21,-21,-21,-21,-21,-21},// 状态29
	    {0, 20, 21, 0,  0,  0,  0,  0,  0,  0,  0,  17, 0,  0,  0,  34, 32, 33, 0,  0,  0,  0,  0,  0,  0},  // 状态30
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  -22,-22,0,  0,  32, 33, 35, 38, 39, 40, 41, 22, 22}, // 状态31
	    {0, -24,-24,0,  0,  0,  0,  0,  0,  0,  0,  -24,0,  0,  0,  -24,-24,-24,0,  0,  0,  0,  0,  0,  0},  // 状态32
	    {0, -25,-25,0,  0,  0,  0,  0,  0,  0,  0,  -25,0,  0,  0,  -25,-25,-25,0,  0,  0,  0,  0,  0,  0},  // 状态33
	    {0, -26,-26,0,  0,  0,  0,  0,  0,  0,  0,  -26,0,  0,  0,  -26,-26,-26,0,  0,  0,  0,  0,  0,  0},  // 状态34
	    {0, -27,-27,0,  0,  0,  0,  0,  0,  0,  0,  -27,0,  0,  0,  -27,-27,-27,0,  0,  0,  0,  0,  0,  0},  // 状态35
	    {0, -28,-28,0,  0,  0,  0,  0,  0,  0,  0,  -28,0,  0,  0,  -28,-28,-28,0,  0,  0,  0,  0,  0,  0},  // 状态36
	    {0, -29,-29,0,  0,  0,  0,  0,  0,  0,  0,  -29,0,  0,  0,  -29,-29,-29,0,  0,  0,  0,  0,  0,  0},  // 状态37
	    {0, -30,-30,0,  0,  0,  0,  0,  0,  0,  0,  -30,0,  0,  0,  -30,-30,-30,0,  0,  0,  0,  0,  0,  0},  // 状态38
	    {0, -31,-31,0,  0,  0,  0,  0,  0,  0,  0,  -31,0,  0,  0,  -31,-31,-31,0,  0,  0,  0,  0,  0,  0},  // 状态39
	    {0, -32,-32,0,  0,  0,  0,  0,  0,  0,  0,  -32,0,  0,  0,  -32,-32,-32,0,  0,  0,  0,  0,  0,  0},  // 状态40
	    {0, -33,-33,0,  0,  0,  0,  0,  0,  0,  0,  -33,0,  0,  0,  -33,-33,-33,0,  0,  0,  0,  0,  0,  0},  // 状态41
	    {0, -34,-34,0,  0,  0,  0,  0,  0,  0,  0,  -34,0,  0,  0,  -34,-34,-34,0,  0,  0,  0,  0,  0,  0},  // 状态42
	    {0, -35,-35,0,  0,  0,  0,  0,  0,  0,  0,  -35,0,  0,  0,  -35,-35,-35,0,  0,  0,  0,  0,  0,  0},  // 状态43
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  45, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态44
	    {0, 20, 21, 0,  0,  0,  0,  0,  0,  0,  0,  17, 0,  0,  0,  34, 32, 33, 0,  0,  0,  0,  0,  0,  0},  // 状态45
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  47, 0,  0,  0,  36, 37, 35, 38, 39, 40, 41, 42, 43}, // 状态46
	    {0, 13, 0,  44, 0,  51, 56, 61, 0,  4,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态47
	    {0, -10,0,  -10,49, -10,-10,-10,-10,-10,-10,0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态48
	    {0, 13, 0,  44, 0,  51, 56, 61, 0,  4,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态49
	    {0, -11,0,  -11,-11,-11,-11,-11,-11,-11,-11,0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态50
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  52, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态51
	    {0, 20, 21, 0,  0,  0,  0,  0,  0,  0,  0,  17, 0,  0,  0,  34, 32, 33, 0,  0,  0,  0,  0,  0,  0},  // 状态52
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  54, 0,  0,  0,  36, 37, 35, 38, 39, 40, 41, 42, 43}, // 状态53
	    {0, 13, 0,  44, 0,  51, 56, 61, 0,  4,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态54
	    {0, -12,0,  -12,-12,-12,-12,-12,-12,-12,-12,0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态55
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  57, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态56
	    {0, 58, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态57
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  59, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态58
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  60, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态59
	    {0, -13,0,  -13,-13,-13,-13,-13,-13,-13,-13,0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态60
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  62, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态61
	    {0, 20, 21, 0,  0,  0,  0,  0,  0,  0,  0,  17, 0,  0,  0,  34, 32, 33, 0,  0,  0,  0,  0,  0,  0},  // 状态62
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  64, 0,  0,  0,  36, 37, 35, 38, 39, 40, 41, 42, 43}, // 状态63
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  65, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态64
	    {0, -14,0,  -14,-14,-14,-14,-14,-14,-14,-14,0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态65
	    {0, -23,0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // 状态66
	};
	
	/** AST根结点 */
	private AbstractSyntaxTree treeRoot;
	/** Token链 */
	private List<Token> tokenList;
	/** AST结点栈 */
	private Stack<Object> symbolStack;
	/** 状态栈 */ 
	private Stack<Integer> stateStack;
	/** Token迭代器 */
	private ListIterator<Token> tokenIterator;
	/** 当前Token */
	private Token nowToken;

	
	@Override
	public AbstractSyntaxTree getAbstractSyntaxTree() {
		return treeRoot;
	}

	@Override
	public void open(List<Token> tokenList) {
		this.tokenList = tokenList;
	}

	@Override
	public AbstractSyntaxTree parse() throws ParserException {
		tokenIterator = tokenList.listIterator();
		symbolStack = new Stack<Object>();
		stateStack = new Stack<Integer>();
		int state = 1;
		int terminal = nextTerminal();
		int nonterminal;
		// 将初始状态号压栈
		stateStack.push(state);
		while (true) {
			// 从Action表中查找动作，并依此进行操作
			int action = actionTable[state][terminal];
			if (action == -1) {
			// 成功
				break;
			} else if (action == 0) {
			// 错误
				throw new ParserException();
			} else if (action > 0) {
			// 移入
				// 状态转移
				state = action;
				// 将新状态号压栈
				stateStack.push(state);
				symbolStack.push(nowToken);
				// 获取下一个终极符
				terminal = nextTerminal();
			} else {
			// 归约
				// 根据产生式编号进行归约
				int production_no = -action;
				switch (production_no) {
				case 2 :
					reduceProgram();
					nonterminal = PROGRAM;
					break;
				case 3 :
					reduceBlock();
					nonterminal = BLOCK;
					break;
				case 4 :
					reduceStmts();
					nonterminal = STMTS;
					break;
				case 5 :
					reduceStmts();
					nonterminal = STMTS;
					break;
				case 6 :
					reduceNullStmts();
					nonterminal = STMTS;
					break;
				case 7 :
					reduceDeclaration();
					nonterminal = DECL;
					break;	
				case 8 :
					reduceBlockStatement();
					nonterminal = STMT;
					break;
				case 9 :
					reduceAssignmentStatement();
					nonterminal = STMT;
					break;
				case 10 :
					reduceIfStatement();
					nonterminal = STMT;
					break;
				case 11 :
					reduceIfElseStatment();
					nonterminal = STMT;
					break;
				case 12 :
					reduceWhileStatement();
					nonterminal = STMT;
					break;
				case 13 :
					reduceInputStatement();
					nonterminal = STMT;
					break;
				case 14 :
					reduceOutputStatement();
					nonterminal = STMT;
					break;
				case 15 :
					reduceIdentifier();
					nonterminal = EXPR;
					break;
				case 16 :
					reduceLiteral();
					nonterminal = EXPR;
					break;
				case 17 :
					reduceParenthesisExpression();
					nonterminal = EXPR;
					break;
				case 18 :
					reduceUnaryExpression();
					nonterminal = EXPR;
					break;
				case 19 :
					reduceBinaryExpression();
					nonterminal = EXPR;
					break;
				case 20 :
					reduceBinaryExpression();
					nonterminal = EXPR;
					break;
				case 21 :
					reduceBinaryExpression();
					nonterminal = EXPR;
					break;
				case 22 :
					reduceBinaryExpression();
					nonterminal = EXPR;
					break;
				case 23 :
					reduceType(PrimitiveType.INTEGER);
					nonterminal = TYPE;
					break;
				case 24 :
					reduceUnaryOperator(UnaryOperator.POSITIVE);
					nonterminal = UN_OP;
					break;
				case 25 :
					reduceUnaryOperator(UnaryOperator.NEGTIVE);
					nonterminal = UN_OP;
					break;
				case 26 :
					reduceUnaryOperator(UnaryOperator.NOT);
					nonterminal = UN_OP;
					break;
				case 27 :
					reduceBinaryOperator(BinaryOperator.TIMES);
					nonterminal = BI_OP1;
					break;
				case 28 :
					reduceBinaryOperator(BinaryOperator.PLUS);
					nonterminal = BI_OP2;
					break;
				case 29 :
					reduceBinaryOperator(BinaryOperator.MINUS);
					nonterminal = BI_OP2;
					break;
				case 30 :
					reduceBinaryOperator(BinaryOperator.LESS);
					nonterminal = BI_OP3;
					break;
				case 31 :
					reduceBinaryOperator(BinaryOperator.LESS_EQUALS);
					nonterminal = BI_OP3;
					break;
				case 32 :
					reduceBinaryOperator(BinaryOperator.GREATER);
					nonterminal = BI_OP3;
					break;
				case 33 :
					reduceBinaryOperator(BinaryOperator.GREATER_EQUALS);
					nonterminal = BI_OP3;
					break;
				case 34 :
					reduceBinaryOperator(BinaryOperator.EQUALS);
					nonterminal = BI_OP4;
					break;
				case 35 :
					reduceBinaryOperator(BinaryOperator.NOT_EQUALS);
					nonterminal = BI_OP4;
					break;
				default:
					throw new ParserException();
				}
				state = stateStack.peek();
				state = gotoTable[state][nonterminal];
				if (state == 0) {
					throw new ParserException();
				}
				// 将新状态号压栈
				stateStack.push(state);
			}
		}
		treeRoot = (AbstractSyntaxTree) symbolStack.pop();
		return treeRoot;
	}
	
	/**
	 * 从Token链中获取下一个终极符
	 * @return 如果Token链中下一个终极符对应的编号
	 * @throws ParserException 
	 */
	private int nextTerminal() throws ParserException {
		if (tokenIterator.hasNext()) {
			// 获取下一个终极符的信息
			nowToken = tokenIterator.next();
			int type = nowToken.getType();
			String lexeme = nowToken.getLexeme();
			// 判断该终极符的编号
			switch (type) {
			case canto.c1.Token.ID :
				return ID;
			case canto.c1.Token.CONSTANT :
				return LITERAL;
			case canto.c1.Token.KEYWORD :				
				if (lexeme.equals("if")) {
					return IF;
				} else if (lexeme.equals("else")) {
					return ELSE;
				} else if (lexeme.equals("while")) {
					return WHILE;
				} else if (lexeme.equals("input")) {
					return INPUT;
				} else if (lexeme.equals("output")) {
					return OUTPUT;
				} else if (lexeme.equals("int")) {
					return INT;
				}
			case canto.c1.Token.PUNCTUATION :
				if (lexeme.equals("{")) {
					return L_BRACE;
				} else if (lexeme.equals("}")) {
					return R_BRACE;
				} else if (lexeme.equals("(")) {
					return L_PARENT;
				} else if (lexeme.equals(")")) {
					return R_PARENT;
				} else if (lexeme.equals(";")) {
					return SEMI;
				}
			case canto.c1.Token.OPERERATOR :
				if (lexeme.equals("=")) {
					return EQUALS;
				} else if (lexeme.equals("!")) {
					return NOT;
				} else if (lexeme.equals( "+")) {
					return PLUS;
				} else if (lexeme.equals("-")) {
					return MINUS;
				} else if (lexeme.equals("*")) {
					return TIMES;
				} else if (lexeme.equals("<")) {
					return LESS;
				} else if (lexeme.equals("<=")) {
					return LESS_EQUALS;
				} else if (lexeme.equals(">")) {
					return GREATER;
				} else if (lexeme.equals(">=")) {
					return GREATER_EQUALS;
				} else if (lexeme.equals("==")) {
					return EQUALS_EQUALS;
				} else if (lexeme.equals("!=")) {
					return NOT_EQUALS;
				}
			default :
				throw new ParserException();
			}
		} else {
			return END;			
		}
	}
	
	/**
	 * 2号产生式
	 * PROGRAM -> BLOCK
	 */
	private void reduceProgram() {
		// 弹出产生式右侧的符号
		Block block = (Block) symbolStack.pop();
		// 创建Program结点并压栈
		symbolStack.push(new Program(block));
		// 状态向上回退1层
		stateStack.pop();
	}
	
	/**
	 * 3号产生式
	 *   BLOCK -> { STMTS }
	 */
	private void reduceBlock() {
		// 弹出产生式右侧的符号
		symbolStack.pop();
		Block block = (Block) symbolStack.pop();
		symbolStack.pop();
		// STMTS已是Block结点，直接将其再压回栈中
		symbolStack.push(block);
		// 状态向上返回3层
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
	}
	
	/**
	 * 4号产生式
	 *   STMTS -> STMTS DECL
	 * 5号产生式
	 *   STMTS -> STMTS STMT
	 */
	private void reduceStmts() {
		// 弹出产生式右侧的符号
		Blockable blockable = (Blockable) symbolStack.pop();
		Block block = (Block) symbolStack.peek();
		// 向栈顶的Block结点添加内容
		block.addBlockable(blockable);
		// 状态向上返回2层
		stateStack.pop();
		stateStack.pop();
	}

	/**
	 * 6号产生式
	 *   STMTS -> ε
	 */
	private void reduceNullStmts() {
		// 该产生式仅在STMTS的首部发生，在此处创建Block结点并压栈
		symbolStack.push(new Block());
	}
	
	/**
	 * 7号产生式
	 *   DECL -> TYPE id ;
	 */
	private void reduceDeclaration() {
		// 弹出产生式右侧的符号
		symbolStack.pop();
		Token token = (Token) symbolStack.pop();
		Type type = (Type) symbolStack.pop();
		// 创建Declaration结点并压栈
		symbolStack.push(new Declaration(type, 
				new Identifier(token.getLexeme())));
		// 状态向上返回3层
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
	}
	
	/**
	 * 8号产生式
	 *   STMT -> BLOCK
	 */
	private void reduceBlockStatement() {
		// Block已是STMT的代表，不做栈操作
		// 状态向上返回1层
		stateStack.pop();
	}

	/**
	 * 9号产生式
	 *   STMT -> id = EXPR ;
	 */
	private void reduceAssignmentStatement() {
		// 弹出产生式右侧的符号
		symbolStack.pop();
		Expression expr = (Expression) symbolStack.pop();
		symbolStack.pop();
		Token token = (Token) symbolStack.pop();
		// 创建AssignmentStatement结点并压栈
		symbolStack.push(new AssignmentStatement(
				new Identifier(token.getLexeme()), expr));
		// 状态向上返回4层
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
	}
	
	/**
	 * 10号产生式
	 *   STMT -> if ( EXPR ) STMT
	 */
	private void reduceIfStatement() {
		// 弹出产生式右侧的符号
		Statement stmt = (Statement) symbolStack.pop();
		symbolStack.pop();
		Expression expr = (Expression) symbolStack.pop();
		symbolStack.pop();
		symbolStack.pop();
		// 创建IfStatement结点并压栈
		symbolStack.push(new IfStatement(expr, stmt));
		// 状态向上返回5层
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
	}
	
	/**
	 * 11号产生式
	 *   STMT -> ( EXPR ) STMT else STMT
	 */
	private void reduceIfElseStatment() {
		// 弹出产生式右侧的符号
		Statement elseStmt = (Statement) symbolStack.pop();
		symbolStack.pop();
		Statement thenStmt = (Statement) symbolStack.pop();
		symbolStack.pop();
		Expression expr = (Expression) symbolStack.pop();
		symbolStack.pop();
		symbolStack.pop();
		// 创建IfStatement结点并压栈
		symbolStack.push(new IfStatement(expr, thenStmt, elseStmt));
		// 状态向上返回7层
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
	}
	
	/**
	 * 12号产生式
	 *   STMT -> while ( EXPR ) STMT
	 */
	private void reduceWhileStatement() {
		// 弹出产生式右侧的符号
		Statement stmt = (Statement) symbolStack.pop();
		symbolStack.pop();
		Expression expr = (Expression) symbolStack.pop();
		symbolStack.pop();
		symbolStack.pop();
		// 创建WhileStatement结点并压栈
		symbolStack.push(new WhileStatement(expr, stmt));
		// 状态向上返回5层
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
	}
	
	/**
	 * 13号产生式
	 *   STMT -> input ( id ) ;
	 */
	private void reduceInputStatement() {
		// 弹出产生式右侧的符号
		symbolStack.pop();
		symbolStack.pop();
		Token token = (Token) symbolStack.pop();
		symbolStack.pop();
		symbolStack.pop();
		// 创建InputStatement结点并压栈
		symbolStack.push(new InputStatement(new Identifier(token.getLexeme())));
		// 状态向上返回5层
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
	}
	
	/**
	 * 14号产生式
	 *   STMT -> output ( EXPR ) ;
	 */
	private void reduceOutputStatement() {
		// 弹出产生式右侧的符号
		symbolStack.pop();
		symbolStack.pop();
		Expression expr = (Expression) symbolStack.pop();
		symbolStack.pop();
		symbolStack.pop();
		// 创建OutputStatement结点并压栈
		symbolStack.push(new OutputStatement(expr));
		// 状态向上返回5层
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
	}
	
	/**
	 * 15产生式
	 *   EXPR -> id
	 */
	private void reduceIdentifier() {
		// 弹出产生式右侧的符号
		Token token = (Token) symbolStack.pop(); 
		// 创建Identifier结点并压栈
		symbolStack.push(new Identifier(token.getLexeme()));
		// 状态向上返回1层
		stateStack.pop();
	}
	
	/**
	 * 16号产生式
	 *   EXPR -> literal
	 */
	private void reduceLiteral() {
		// 弹出产生式右侧的符号
		Token token = (Token) symbolStack.pop();
		// 创建Literal结点并压栈
		symbolStack.push(new IntegerLiteral((Integer)token.getAttribute()));
		// 状态向上返回1层
		stateStack.pop();
	}
	
	/**
	 * 17号产生式
	 *   EXPR -> ( EXPR )
	 */
	private void reduceParenthesisExpression() {
		// 弹出产生式右侧的符号
		symbolStack.pop();
		Expression expression = (Expression) symbolStack.pop();
		symbolStack.pop();
		// 不创建新的AST结点，直接将原结点压栈
		symbolStack.push(expression);
		// 状态向上返回3层
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
	}
	
	/**
	 * 18号产生式
	 *   EXPR -> UN_OP EXPR
	 */
	private void reduceUnaryExpression() {
		// 弹出产生式右侧的符号
		Expression expr = (Expression) symbolStack.pop();
		UnaryOperator unOp = (UnaryOperator) symbolStack.pop();
		// 创建UnaryOperator结点并压栈
		symbolStack.push(new UnaryExpression(unOp, expr));
		// 状态向上返回2层
		stateStack.pop();
		stateStack.pop();
	}
	
	/**
	 * 19号产生式
	 *   EXPR -> EXPR BI_OP1 EXPR
	 * 20号产生式
	 *   EXPR -> EXPR BI_OP2 EXPR
	 * 21号产生式
	 *   EXPR -> EXPR BI_OP3 EXPR
	 * 22号产生式
	 *   EXPR -> EXPR BI_OP4 EXPR
	 */
	private void reduceBinaryExpression() {
		// 弹出产生式右侧的符号
		Expression rightExpr = (Expression) symbolStack.pop();
		BinaryOperator biOp = (BinaryOperator) symbolStack.pop();
		Expression leftExpr = (Expression) symbolStack.pop();
		// 创建BinaryOperator结点并压栈
		symbolStack.push(new BinaryExpression(biOp, leftExpr, rightExpr));
		// 状态向上返回3层
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
	}

	/**
	 * 23号产生式
	 *   TYPE -> int
	 * @param type 类型编号
	 */
	private void reduceType(int type) {
		// 弹出产生式右侧的符号
		symbolStack.pop();
		// 创建Type结点并压栈
		symbolStack.push(new PrimitiveType(type));
		// 状态向上返回1层
		stateStack.pop();
	}
	
	/**
	 * 24-26号产生式
	 *   UN_OP -> + 
	 *   UN_OP -> - 
	 *   UN_OP -> !
	 * @param op 单元操作符编号
	 */
	private void reduceUnaryOperator(int op) {
		// 弹出产生式右侧的符号
		symbolStack.pop();
		// 创建UnaryOperatorType结点并压栈
		symbolStack.push(new UnaryOperator(op));
		// 状态向上返回1层
		stateStack.pop();
	}
	
	/**
	 * 27-35号产生式
	 *   BI_OP1 -> *
	 *   BI_OP2 -> + 
	 *   BI_OP2 -> - 
	 *   BI_OP3 -> < 
	 *   BI_OP3 -> <= 
	 *   BI_OP3 -> > 
	 *   BI_OP3 -> >= 
	 *   BI_OP4 -> == 
	 *   BI_OP4 -> != 
	 * @param op 二元操作符编号
	 */
	private void reduceBinaryOperator(int op) {
		// 弹出产生式右侧的符号
		symbolStack.pop();
		// 创建BinaryOperatorType结点并压栈
		symbolStack.push(new BinaryOperator(op));
		// 状态向上返回1层
		stateStack.pop();
	}
	
}
