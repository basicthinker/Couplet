package canto.c1;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import canto.AbstractSyntaxTree;
import canto.Token;
import canto.c1.ast.*;
import canto.c1.exception.ParserException;

/**
 * C1语言的语法分析器 
 */
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
	
	
	/** 
	 * Goto表：
	 *   该表第零行不使用，行号对应状态号，列号对应相应编号的非终极符；
	 *   表中零表示错误；
	 *   表中正整数表示转移的目标状态。 
	 */
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
	
	/** 
	 * Action表：
	 *   该表第零行不使用，行号对应状态号，列号对应相应编号的终极符；
	 *   表中零表示错误；
	 *   表中正整数表示移入并转移至该状态；
	 *   表中负整数表示用编号为该数绝对值的产生式归约。
	 */
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
	
	/** 生成的AST根结点 */
	private AbstractSyntaxTree treeRoot;
	
	/** 输入的Token链 */
	private List<Token> tokenList;
	
	/** 语法分析时用于存放AST结点的栈 */
	private Stack<AbstractSyntaxTree> nodeStack;
	
	/** 语法分析时用于存放状态号的栈 */ 
	private Stack<Integer> stateStack;
	
	/** 用于获取下一个Token的迭代器 */
	private Iterator<Token> tokenIterator;
	
	/** 存放当前分析到的Token */
	private Token nowToken;

	
	@Override
	public AbstractSyntaxTree getAbstractSyntaxTree() {
		return treeRoot;
	}

	@Override
	public void setTokenList(List<Token> tokenList) {
		this.tokenList = tokenList;
	}

	@Override
	public AbstractSyntaxTree parse() throws ParserException {
		// 从Token链中获取迭代器
		tokenIterator = tokenList.iterator();
		// 初始化栈
		nodeStack = new Stack<AbstractSyntaxTree>();
		stateStack = new Stack<Integer>();
		// 初始化终极符、非终极符、状态号
		int terminal = nextTerminal();
		int nonterminal;
		int state = 1;
		stateStack.push(state);
		// 逐个获取Token分析，直到成功或产生错误
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
				// 将某些终极符转换成AST结点压栈
				switch (terminal) {
				case ID :
					nodeStack.push(new Identifier(nowToken.getLexeme()));
					break;
				case LITERAL :
					nodeStack.push(new IntegerLiteral((Integer)nowToken.getAttribute()));
					break;
				}
				// 状态转移，并将新状态号压栈
				state = action;
				stateStack.push(state);
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
				case 4 : case 5 :
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
				case 19 : case 20 : case 21 : case 22 :
					reduceBinaryExpression();
					nonterminal = EXPR;
					break;
				case 23 :
					reduceType();
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
				// 从状态栈顶获取当前状态号
				state = stateStack.peek();
				// 根据归约形成的非终极符和当前状态进行状态转移
				state = gotoTable[state][nonterminal];
				// 判断该转移是否合法
				if (state == 0) {
				// 产生错误
					throw new ParserException();
				} else {
				// 否则，将新状态号压栈
					stateStack.push(state);
				}
			}
		}
		// 将分析完成后生成的AST根结点弹出栈并返回
		treeRoot = (AbstractSyntaxTree) nodeStack.pop();
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
	 * 重复n次从状态栈中弹出元素
	 * @param n 重复弹栈的次数
	 */
	private void popStateStack(int n) {
		for (int i = 0; i < n; i++) stateStack.pop();
	}
	
	/**
	 * 归约2号产生式
	 *   PROGRAM -> BLOCK
	 */
	private void reduceProgram() {
		// 弹出产生式右侧的AST结点
		Block block = (Block) nodeStack.pop();
		// 创建Program结点并压栈
		nodeStack.push(new Program(block));
		// 状态向上返回1层
		popStateStack(1);
	}
	
	/**
	 * 归约3号产生式
	 *   BLOCK -> { STMTS }
	 */
	private void reduceBlock() {
		// 弹出产生式右侧的AST结点
		StatementList list = (StatementList) nodeStack.pop();
		// 创建Block结点并压栈
		nodeStack.push(new Block(list));
		// 状态向上返回3层
		popStateStack(3);
	}
	
	/**
	 * 归约4-5号产生式
	 *   STMTS -> STMTS DECL
	 *   STMTS -> STMTS STMT
	 */
	private void reduceStmts() {
		// 弹出产生式右侧的AST结点
		Listable listable = (Listable) nodeStack.pop();
		// 由于StatementList结点还要压回栈中，不弹出
		StatementList list = (StatementList) nodeStack.peek();
		// 向栈顶的StatementList结点添加内容
		list.addListable(listable);
		// 状态向上返回2层
		popStateStack(2);
	}

	/**
	 * 归约6号产生式
	 *   STMTS -> ε
	 */
	private void reduceNullStmts() {
		// 该产生式仅在STMTS的首部发生，在此处创建StatementList结点并压栈
		nodeStack.push(new StatementList());
	}
	
	/**
	 * 归约7号产生式
	 *   DECL -> TYPE id ;
	 */
	private void reduceDeclaration() {
		// 弹出产生式右侧的AST结点
		Identifier id = (Identifier) nodeStack.pop();
		Type type = (Type) nodeStack.pop();
		// 创建Declaration结点并压栈
		nodeStack.push(new Declaration(type, id));
		// 状态向上返回3层
		popStateStack(3);
	}
	
	/**
	 * 归约8号产生式
	 *   STMT -> BLOCK
	 */
	private void reduceBlockStatement() {
		// Block结点已是Statement的子类，不做AST栈的操作
		// 状态向上返回1层
		popStateStack(1);
	}

	/**
	 * 归约9号产生式
	 *   STMT -> id = EXPR ;
	 */
	private void reduceAssignmentStatement() {
		// 弹出产生式右侧的AST结点
		Expression expr = (Expression) nodeStack.pop();
		Identifier id = (Identifier) nodeStack.pop();
		// 创建AssignmentStatement结点并压栈
		nodeStack.push(new AssignmentStatement(id, expr));
		// 状态向上返回4层
		popStateStack(4);
	}
	
	/**
	 * 归约10号产生式
	 *   STMT -> if ( EXPR ) STMT
	 */
	private void reduceIfStatement() {
		// 弹出产生式右侧的AST结点
		Statement stmt = (Statement) nodeStack.pop();
		Expression expr = (Expression) nodeStack.pop();
		// 创建IfStatement结点并压栈
		nodeStack.push(new IfStatement(expr, stmt));
		// 状态向上返回5层
		popStateStack(5);
	}
	
	/**
	 * 归约11号产生式
	 *   STMT -> ( EXPR ) STMT else STMT
	 */
	private void reduceIfElseStatment() {
		// 弹出产生式右侧的AST结点
		Statement elseStmt = (Statement) nodeStack.pop();
		Statement thenStmt = (Statement) nodeStack.pop();
		Expression expr = (Expression) nodeStack.pop();
		// 创建IfStatement结点并压栈
		nodeStack.push(new IfStatement(expr, thenStmt, elseStmt));
		// 状态向上返回7层
		popStateStack(7);
	}
	
	/**
	 * 归约12号产生式
	 *   STMT -> while ( EXPR ) STMT
	 */
	private void reduceWhileStatement() {
		// 弹出产生式右侧的AST结点
		Statement stmt = (Statement) nodeStack.pop();
		Expression expr = (Expression) nodeStack.pop();
		// 创建WhileStatement结点并压栈
		nodeStack.push(new WhileStatement(expr, stmt));
		// 状态向上返回5层
		popStateStack(5);
	}
	
	/**
	 * 归约13号产生式
	 *   STMT -> input ( id ) ;
	 */
	private void reduceInputStatement() {
		// 弹出产生式右侧的AST结点
		Identifier id = (Identifier) nodeStack.pop();
		// 创建InputStatement结点并压栈
		nodeStack.push(new InputStatement(id));
		// 状态向上返回5层
		popStateStack(5);
	}
	
	/**
	 * 归约14号产生式
	 *   STMT -> output ( EXPR ) ;
	 */
	private void reduceOutputStatement() {
		// 弹出产生式右侧的AST结点
		Expression expr = (Expression) nodeStack.pop();
		// 创建OutputStatement结点并压栈
		nodeStack.push(new OutputStatement(expr));
		// 状态向上返回5层
		popStateStack(5);
	}
	
	/**
	 * 归约15产生式
	 *   EXPR -> id
	 */
	private void reduceIdentifier() {
		// Identifier已是Expression的子类，不做AST栈的操作
		// 状态向上返回1层
		popStateStack(1);
	}
	
	/**
	 * 归约16号产生式
	 *   EXPR -> literal
	 */
	private void reduceLiteral() {
		// Literal已是Expression的子类，不做AST栈的操作
		// 状态向上返回1层
		popStateStack(1);
	}
	
	/**
	 * 归约17号产生式
	 *   EXPR -> ( EXPR )
	 */
	private void reduceParenthesisExpression() {
		// 弹出产生式右侧的AST结点
		Expression expression = (Expression) nodeStack.pop();
		// 不创建新的AST结点，直接将原结点压栈
		nodeStack.push(expression);
		// 状态向上返回3层
		popStateStack(3);
	}
	
	/**
	 * 归约18号产生式
	 *   EXPR -> UN_OP EXPR
	 */
	private void reduceUnaryExpression() {
		// 弹出产生式右侧的AST结点
		Expression expr = (Expression) nodeStack.pop();
		UnaryOperator unOp = (UnaryOperator) nodeStack.pop();
		// 创建UnaryOperator结点并压栈
		nodeStack.push(new UnaryExpression(unOp, expr));
		// 状态向上返回2层
		popStateStack(2);
	}
	
	/**
	 * 归约19-22号产生式
	 *   EXPR -> EXPR BI_OP1 EXPR
	 *   EXPR -> EXPR BI_OP2 EXPR
	 *   EXPR -> EXPR BI_OP3 EXPR
	 *   EXPR -> EXPR BI_OP4 EXPR
	 */
	private void reduceBinaryExpression() {
		// 弹出产生式右侧的AST结点
		Expression rightExpr = (Expression) nodeStack.pop();
		BinaryOperator biOp = (BinaryOperator) nodeStack.pop();
		Expression leftExpr = (Expression) nodeStack.pop();
		// 创建BinaryOperator结点并压栈
		nodeStack.push(new BinaryExpression(biOp, leftExpr, rightExpr));
		// 状态向上返回3层
		popStateStack(3);
	}

	/**
	 * 归约23号产生式
	 *   TYPE -> int
	 * @param type 类型编号
	 */
	private void reduceType() {
		// 产生式右侧无AST结点，不做AST栈的操作
		// 创建Type结点并压栈
		nodeStack.push(new IntegerType());
		// 状态向上返回1层
		popStateStack(1);
	}
	
	/**
	 * 归约24-26号产生式
	 *   UN_OP -> + 
	 *   UN_OP -> - 
	 *   UN_OP -> !
	 * @param op 单元操作符编号
	 */
	private void reduceUnaryOperator(int op) {
		// 产生式右侧无AST结点，不做AST栈的操作
		// 创建UnaryOperatorType结点并压栈
		nodeStack.push(new UnaryOperator(op));
		// 状态向上返回1层
		popStateStack(1);
	}
	
	/**
	 * 归约27-35号产生式
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
		// 产生式右侧无AST结点，不做AST栈的操作
		// 创建BinaryOperatorType结点并压栈
		nodeStack.push(new BinaryOperator(op));
		// 状态向上返回1层
		popStateStack(1);
	}
	
}
