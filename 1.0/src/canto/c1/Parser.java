package canto.c1;

import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

import canto.AbstractSyntaxTree;
import canto.Token;
import canto.c1.ast.*;
import canto.c1.exception.ParserException;

public class Parser implements canto.Parser {
	
	/** ���ռ�����ų��� */
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
	
	/** �ռ�����ų��� */
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
	
	
	/** Goto�� */
	private static int[][] gotoTable = {
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬0 ��ʹ��
	    {2,  3,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬1
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬2
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬3
	    {0,  0,  5,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬4
	    {0,  12, 0,  7,  8,  0,  9,  0,  0,  0,  0,  0},  // ״̬5
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬6
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬7
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬8
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬9
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬10
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬11
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬12
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬13
	    {0,  0,  0,  0,  0,  15, 0,  22, 0,  0,  0,  0},  // ״̬14
	    {0,  0,  0,  0,  0,  0,  0,  0,  24, 26, 28, 30}, // ״̬15
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬16
	    {0,  0,  0,  0,  0,  18, 0,  22, 0,  0,  0,  0},  // ״̬17
	    {0,  0,  0,  0,  0,  0,  0,  0,  24, 26, 28, 30}, // ״̬18
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬19
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬20
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬21
	    {0,  0,  0,  0,  0,  23, 0,  22, 0,  0,  0,  0},  // ״̬22
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬23
	    {0,  0,  0,  0,  0,  25, 0,  22, 0,  0,  0,  0},  // ״̬24
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬25
	    {0,  0,  0,  0,  0,  27, 0,  22, 0,  0,  0,  0},  // ״̬26
	    {0,  0,  0,  0,  0,  0,  0,  0,  24, 0,  0,  0},  // ״̬27
	    {0,  0,  0,  0,  0,  29, 0,  22, 0,  0,  0,  0},  // ״̬28
	    {0,  0,  0,  0,  0,  0,  0,  0,  24, 26, 0,  0},  // ״̬29
	    {0,  0,  0,  0,  0,  31, 0,  22, 0,  0,  0,  0},  // ״̬30
	    {0,  0,  0,  0,  0,  0,  0,  0,  24, 26, 28, 0},  // ״̬31
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬32
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬33
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬34
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬35
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬36
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬37
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬38
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬39
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬40
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬41
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬42
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬43
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬44
	    {0,  0,  0,  0,  0,  46, 0,  22, 0,  0,  0,  0},  // ״̬45
	    {0,  0,  0,  0,  0,  0,  0,  0,  24, 26, 28, 30}, // ״̬46
	    {0,  12, 0,  0,  48, 0,  0,  0,  0,  0,  0,  0},  // ״̬47
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬48
	    {0,  12, 0,  0,  50, 0,  0,  0,  0,  0,  0,  0},  // ״̬49
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬50
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬51
	    {0,  0,  0,  0,  0,  53, 0,  22, 0,  0,  0,  0},  // ״̬52
	    {0,  0,  0,  0,  0,  0,  0,  0,  24, 26, 28, 30}, // ״̬53
	    {0,  12, 0,  0,  55, 0,  0,  0,  0,  0,  0,  0},  // ״̬54
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬55
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬56
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬57
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬58
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬59
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬60
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬61
	    {0,  0,  0,  0,  0,  63, 0,  22, 0,  0,  0,  0},  // ״̬62
	    {0,  0,  0,  0,  0,  0,  0,  0,  24, 26, 28, 30}, // ״̬63
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬64
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬65
	    {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬66
	};
	
	/** Action�� */
	private static int[][] actionTable = {
		{0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬0 ��ʹ��
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  4,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬1
	    {-1,0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬2
	    {-2,0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬3
	    {0, -6, 0,  -6, 0,  -6, -6, -6, -6, -6, -6, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬4
	    {0, 13, 0,  44, 0,  51, 56, 61, 66, 4,  6,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬5
	    {-3,-3, 0,  -3, -3, -3, -3, -3, -3, -3, -3, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬6
	    {0, -4, 0,  -4, -4, -4, -4, -4, -4, -4, -4, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬7
	    {0, -5, 0,  -5, -5, -5, -5, -5, -5, -5, -5, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬8
	    {0, 10, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬9
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  11, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬10
	    {0, -7, 0,  -7, -7, -7, -7, -7, -7, -7, -7, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬11
	    {0, -8, 0,  -8, -8, -8, -8, -8, -8, -8, -8, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬12
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  14, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬13
	    {0, 20, 21, 0,  0,  0,  0,  0,  0,  0,  0,  17, 0,  0,  0,  34, 32, 33, 0,  0,  0,  0,  0,  0,  0},  // ״̬14
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  16, 0,  0,  36, 37, 35, 38, 39, 40, 41, 42, 43}, // ״̬15
	    {0, -9, 0,  -9, -9, -9, -9, -9, -9, -9, -9, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬16
	    {0, 20, 21, 0,  0,  0,  0,  0,  0,  0,  0,  17, 0,  0,  0,  34, 32, 33, 0,  0,  0,  0,  0,  0,  0},  // ״̬17
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  19, 0,  0,  0,  36, 37, 35, 38, 39, 40, 41, 42, 43}, // ״̬18
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  -17,-17,0,  0,  -17,-17,-17,-17,-17,-17,-17,-17,-17},// ״̬19
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  -15,-15,0,  0,  -15,-15,-15,-15,-15,-15,-15,-15,-15},// ״̬20
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  -16,-16,0,  0,  -16,-16,-16,-16,-16,-16,-16,-16,-16},// ״̬21
	    {0, 20, 21, 0,  0,  0,  0,  0,  0,  0,  0,  17, 0,  0,  0,  34, 32, 33, 0,  0,  0,  0,  0,  0,  0},  // ״̬22
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  -18,-18,0,  0,  -18,-18,-18,-18,-18,-18,-18,-18,-18},// ״̬23
	    {0, 20, 21, 0,  0,  0,  0,  0,  0,  0,  0,  17, 0,  0,  0,  34, 32, 33, 0,  0,  0,  0,  0,  0,  0},  // ״̬24
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  -19,-19,0,  0,  -19,-19,-19,-19,-19,-19,-19,-19,-19},// ״̬25
	    {0, 20, 21, 0,  0,  0,  0,  0,  0,  0,  0,  17, 0,  0,  0,  34, 32, 33, 0,  0,  0,  0,  0,  0,  0},  // ״̬26
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  -20,-20,0,  0,  -20,-20,35, -20,-20,-20,-20,-20,-20},// ״̬27
	    {0, 20, 21, 0,  0,  0,  0,  0,  0,  0,  0,  17, 0,  0,  0,  34, 32, 33, 0,  0,  0,  0,  0,  0,  0},  // ״̬28
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  -21,-21,0,  0,  32, 33, 35, -21,-21,-21,-21,-21,-21},// ״̬29
	    {0, 20, 21, 0,  0,  0,  0,  0,  0,  0,  0,  17, 0,  0,  0,  34, 32, 33, 0,  0,  0,  0,  0,  0,  0},  // ״̬30
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  -22,-22,0,  0,  32, 33, 35, 38, 39, 40, 41, 22, 22}, // ״̬31
	    {0, -24,-24,0,  0,  0,  0,  0,  0,  0,  0,  -24,0,  0,  0,  -24,-24,-24,0,  0,  0,  0,  0,  0,  0},  // ״̬32
	    {0, -25,-25,0,  0,  0,  0,  0,  0,  0,  0,  -25,0,  0,  0,  -25,-25,-25,0,  0,  0,  0,  0,  0,  0},  // ״̬33
	    {0, -26,-26,0,  0,  0,  0,  0,  0,  0,  0,  -26,0,  0,  0,  -26,-26,-26,0,  0,  0,  0,  0,  0,  0},  // ״̬34
	    {0, -27,-27,0,  0,  0,  0,  0,  0,  0,  0,  -27,0,  0,  0,  -27,-27,-27,0,  0,  0,  0,  0,  0,  0},  // ״̬35
	    {0, -28,-28,0,  0,  0,  0,  0,  0,  0,  0,  -28,0,  0,  0,  -28,-28,-28,0,  0,  0,  0,  0,  0,  0},  // ״̬36
	    {0, -29,-29,0,  0,  0,  0,  0,  0,  0,  0,  -29,0,  0,  0,  -29,-29,-29,0,  0,  0,  0,  0,  0,  0},  // ״̬37
	    {0, -30,-30,0,  0,  0,  0,  0,  0,  0,  0,  -30,0,  0,  0,  -30,-30,-30,0,  0,  0,  0,  0,  0,  0},  // ״̬38
	    {0, -31,-31,0,  0,  0,  0,  0,  0,  0,  0,  -31,0,  0,  0,  -31,-31,-31,0,  0,  0,  0,  0,  0,  0},  // ״̬39
	    {0, -32,-32,0,  0,  0,  0,  0,  0,  0,  0,  -32,0,  0,  0,  -32,-32,-32,0,  0,  0,  0,  0,  0,  0},  // ״̬40
	    {0, -33,-33,0,  0,  0,  0,  0,  0,  0,  0,  -33,0,  0,  0,  -33,-33,-33,0,  0,  0,  0,  0,  0,  0},  // ״̬41
	    {0, -34,-34,0,  0,  0,  0,  0,  0,  0,  0,  -34,0,  0,  0,  -34,-34,-34,0,  0,  0,  0,  0,  0,  0},  // ״̬42
	    {0, -35,-35,0,  0,  0,  0,  0,  0,  0,  0,  -35,0,  0,  0,  -35,-35,-35,0,  0,  0,  0,  0,  0,  0},  // ״̬43
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  45, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬44
	    {0, 20, 21, 0,  0,  0,  0,  0,  0,  0,  0,  17, 0,  0,  0,  34, 32, 33, 0,  0,  0,  0,  0,  0,  0},  // ״̬45
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  47, 0,  0,  0,  36, 37, 35, 38, 39, 40, 41, 42, 43}, // ״̬46
	    {0, 13, 0,  44, 0,  51, 56, 61, 0,  4,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬47
	    {0, -10,0,  -10,49, -10,-10,-10,-10,-10,-10,0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬48
	    {0, 13, 0,  44, 0,  51, 56, 61, 0,  4,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬49
	    {0, -11,0,  -11,-11,-11,-11,-11,-11,-11,-11,0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬50
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  52, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬51
	    {0, 20, 21, 0,  0,  0,  0,  0,  0,  0,  0,  17, 0,  0,  0,  34, 32, 33, 0,  0,  0,  0,  0,  0,  0},  // ״̬52
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  54, 0,  0,  0,  36, 37, 35, 38, 39, 40, 41, 42, 43}, // ״̬53
	    {0, 13, 0,  44, 0,  51, 56, 61, 0,  4,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬54
	    {0, -12,0,  -12,-12,-12,-12,-12,-12,-12,-12,0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬55
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  57, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬56
	    {0, 58, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬57
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  59, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬58
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  60, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬59
	    {0, -13,0,  -13,-13,-13,-13,-13,-13,-13,-13,0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬60
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  62, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬61
	    {0, 20, 21, 0,  0,  0,  0,  0,  0,  0,  0,  17, 0,  0,  0,  34, 32, 33, 0,  0,  0,  0,  0,  0,  0},  // ״̬62
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  64, 0,  0,  0,  36, 37, 35, 38, 39, 40, 41, 42, 43}, // ״̬63
	    {0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  65, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬64
	    {0, -14,0,  -14,-14,-14,-14,-14,-14,-14,-14,0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬65
	    {0, -23,0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},  // ״̬66
	};
	
	/** AST����� */
	private AbstractSyntaxTree treeRoot;
	/** Token�� */
	private List<Token> tokenList;
	/** AST���ջ */
	private Stack<Object> symbolStack;
	/** ״̬ջ */ 
	private Stack<Integer> stateStack;
	/** Token������ */
	private ListIterator<Token> tokenIterator;
	/** ��ǰToken */
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
		// ����ʼ״̬��ѹջ
		stateStack.push(state);
		while (true) {
			// ��Action���в��Ҷ����������˽��в���
			int action = actionTable[state][terminal];
			if (action == -1) {
			// �ɹ�
				break;
			} else if (action == 0) {
			// ����
				throw new ParserException();
			} else if (action > 0) {
			// ����
				// ״̬ת��
				state = action;
				// ����״̬��ѹջ
				stateStack.push(state);
				symbolStack.push(nowToken);
				// ��ȡ��һ���ռ���
				terminal = nextTerminal();
			} else {
			// ��Լ
				// ���ݲ���ʽ��Ž��й�Լ
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
				// ����״̬��ѹջ
				stateStack.push(state);
			}
		}
		treeRoot = (AbstractSyntaxTree) symbolStack.pop();
		return treeRoot;
	}
	
	/**
	 * ��Token���л�ȡ��һ���ռ���
	 * @return ���Token������һ���ռ�����Ӧ�ı��
	 * @throws ParserException 
	 */
	private int nextTerminal() throws ParserException {
		if (tokenIterator.hasNext()) {
			// ��ȡ��һ���ռ�������Ϣ
			nowToken = tokenIterator.next();
			int type = nowToken.getType();
			String lexeme = nowToken.getLexeme();
			// �жϸ��ռ����ı��
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
	 * 2�Ų���ʽ
	 * PROGRAM -> BLOCK
	 */
	private void reduceProgram() {
		// ��������ʽ�Ҳ�ķ���
		Block block = (Block) symbolStack.pop();
		// ����Program��㲢ѹջ
		symbolStack.push(new Program(block));
		// ״̬���ϻ���1��
		stateStack.pop();
	}
	
	/**
	 * 3�Ų���ʽ
	 *   BLOCK -> { STMTS }
	 */
	private void reduceBlock() {
		// ��������ʽ�Ҳ�ķ���
		symbolStack.pop();
		Block block = (Block) symbolStack.pop();
		symbolStack.pop();
		// STMTS����Block��㣬ֱ�ӽ�����ѹ��ջ��
		symbolStack.push(block);
		// ״̬���Ϸ���3��
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
	}
	
	/**
	 * 4�Ų���ʽ
	 *   STMTS -> STMTS DECL
	 * 5�Ų���ʽ
	 *   STMTS -> STMTS STMT
	 */
	private void reduceStmts() {
		// ��������ʽ�Ҳ�ķ���
		Blockable blockable = (Blockable) symbolStack.pop();
		Block block = (Block) symbolStack.peek();
		// ��ջ����Block����������
		block.addBlockable(blockable);
		// ״̬���Ϸ���2��
		stateStack.pop();
		stateStack.pop();
	}

	/**
	 * 6�Ų���ʽ
	 *   STMTS -> ��
	 */
	private void reduceNullStmts() {
		// �ò���ʽ����STMTS���ײ��������ڴ˴�����Block��㲢ѹջ
		symbolStack.push(new Block());
	}
	
	/**
	 * 7�Ų���ʽ
	 *   DECL -> TYPE id ;
	 */
	private void reduceDeclaration() {
		// ��������ʽ�Ҳ�ķ���
		symbolStack.pop();
		Token token = (Token) symbolStack.pop();
		Type type = (Type) symbolStack.pop();
		// ����Declaration��㲢ѹջ
		symbolStack.push(new Declaration(type, 
				new Identifier(token.getLexeme())));
		// ״̬���Ϸ���3��
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
	}
	
	/**
	 * 8�Ų���ʽ
	 *   STMT -> BLOCK
	 */
	private void reduceBlockStatement() {
		// Block����STMT�Ĵ�������ջ����
		// ״̬���Ϸ���1��
		stateStack.pop();
	}

	/**
	 * 9�Ų���ʽ
	 *   STMT -> id = EXPR ;
	 */
	private void reduceAssignmentStatement() {
		// ��������ʽ�Ҳ�ķ���
		symbolStack.pop();
		Expression expr = (Expression) symbolStack.pop();
		symbolStack.pop();
		Token token = (Token) symbolStack.pop();
		// ����AssignmentStatement��㲢ѹջ
		symbolStack.push(new AssignmentStatement(
				new Identifier(token.getLexeme()), expr));
		// ״̬���Ϸ���4��
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
	}
	
	/**
	 * 10�Ų���ʽ
	 *   STMT -> if ( EXPR ) STMT
	 */
	private void reduceIfStatement() {
		// ��������ʽ�Ҳ�ķ���
		Statement stmt = (Statement) symbolStack.pop();
		symbolStack.pop();
		Expression expr = (Expression) symbolStack.pop();
		symbolStack.pop();
		symbolStack.pop();
		// ����IfStatement��㲢ѹջ
		symbolStack.push(new IfStatement(expr, stmt));
		// ״̬���Ϸ���5��
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
	}
	
	/**
	 * 11�Ų���ʽ
	 *   STMT -> ( EXPR ) STMT else STMT
	 */
	private void reduceIfElseStatment() {
		// ��������ʽ�Ҳ�ķ���
		Statement elseStmt = (Statement) symbolStack.pop();
		symbolStack.pop();
		Statement thenStmt = (Statement) symbolStack.pop();
		symbolStack.pop();
		Expression expr = (Expression) symbolStack.pop();
		symbolStack.pop();
		symbolStack.pop();
		// ����IfStatement��㲢ѹջ
		symbolStack.push(new IfStatement(expr, thenStmt, elseStmt));
		// ״̬���Ϸ���7��
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
	}
	
	/**
	 * 12�Ų���ʽ
	 *   STMT -> while ( EXPR ) STMT
	 */
	private void reduceWhileStatement() {
		// ��������ʽ�Ҳ�ķ���
		Statement stmt = (Statement) symbolStack.pop();
		symbolStack.pop();
		Expression expr = (Expression) symbolStack.pop();
		symbolStack.pop();
		symbolStack.pop();
		// ����WhileStatement��㲢ѹջ
		symbolStack.push(new WhileStatement(expr, stmt));
		// ״̬���Ϸ���5��
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
	}
	
	/**
	 * 13�Ų���ʽ
	 *   STMT -> input ( id ) ;
	 */
	private void reduceInputStatement() {
		// ��������ʽ�Ҳ�ķ���
		symbolStack.pop();
		symbolStack.pop();
		Token token = (Token) symbolStack.pop();
		symbolStack.pop();
		symbolStack.pop();
		// ����InputStatement��㲢ѹջ
		symbolStack.push(new InputStatement(new Identifier(token.getLexeme())));
		// ״̬���Ϸ���5��
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
	}
	
	/**
	 * 14�Ų���ʽ
	 *   STMT -> output ( EXPR ) ;
	 */
	private void reduceOutputStatement() {
		// ��������ʽ�Ҳ�ķ���
		symbolStack.pop();
		symbolStack.pop();
		Expression expr = (Expression) symbolStack.pop();
		symbolStack.pop();
		symbolStack.pop();
		// ����OutputStatement��㲢ѹջ
		symbolStack.push(new OutputStatement(expr));
		// ״̬���Ϸ���5��
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
	}
	
	/**
	 * 15����ʽ
	 *   EXPR -> id
	 */
	private void reduceIdentifier() {
		// ��������ʽ�Ҳ�ķ���
		Token token = (Token) symbolStack.pop(); 
		// ����Identifier��㲢ѹջ
		symbolStack.push(new Identifier(token.getLexeme()));
		// ״̬���Ϸ���1��
		stateStack.pop();
	}
	
	/**
	 * 16�Ų���ʽ
	 *   EXPR -> literal
	 */
	private void reduceLiteral() {
		// ��������ʽ�Ҳ�ķ���
		Token token = (Token) symbolStack.pop();
		// ����Literal��㲢ѹջ
		symbolStack.push(new IntegerLiteral((Integer)token.getAttribute()));
		// ״̬���Ϸ���1��
		stateStack.pop();
	}
	
	/**
	 * 17�Ų���ʽ
	 *   EXPR -> ( EXPR )
	 */
	private void reduceParenthesisExpression() {
		// ��������ʽ�Ҳ�ķ���
		symbolStack.pop();
		Expression expression = (Expression) symbolStack.pop();
		symbolStack.pop();
		// �������µ�AST��㣬ֱ�ӽ�ԭ���ѹջ
		symbolStack.push(expression);
		// ״̬���Ϸ���3��
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
	}
	
	/**
	 * 18�Ų���ʽ
	 *   EXPR -> UN_OP EXPR
	 */
	private void reduceUnaryExpression() {
		// ��������ʽ�Ҳ�ķ���
		Expression expr = (Expression) symbolStack.pop();
		UnaryOperator unOp = (UnaryOperator) symbolStack.pop();
		// ����UnaryOperator��㲢ѹջ
		symbolStack.push(new UnaryExpression(unOp, expr));
		// ״̬���Ϸ���2��
		stateStack.pop();
		stateStack.pop();
	}
	
	/**
	 * 19�Ų���ʽ
	 *   EXPR -> EXPR BI_OP1 EXPR
	 * 20�Ų���ʽ
	 *   EXPR -> EXPR BI_OP2 EXPR
	 * 21�Ų���ʽ
	 *   EXPR -> EXPR BI_OP3 EXPR
	 * 22�Ų���ʽ
	 *   EXPR -> EXPR BI_OP4 EXPR
	 */
	private void reduceBinaryExpression() {
		// ��������ʽ�Ҳ�ķ���
		Expression rightExpr = (Expression) symbolStack.pop();
		BinaryOperator biOp = (BinaryOperator) symbolStack.pop();
		Expression leftExpr = (Expression) symbolStack.pop();
		// ����BinaryOperator��㲢ѹջ
		symbolStack.push(new BinaryExpression(biOp, leftExpr, rightExpr));
		// ״̬���Ϸ���3��
		stateStack.pop();
		stateStack.pop();
		stateStack.pop();
	}

	/**
	 * 23�Ų���ʽ
	 *   TYPE -> int
	 * @param type ���ͱ��
	 */
	private void reduceType(int type) {
		// ��������ʽ�Ҳ�ķ���
		symbolStack.pop();
		// ����Type��㲢ѹջ
		symbolStack.push(new PrimitiveType(type));
		// ״̬���Ϸ���1��
		stateStack.pop();
	}
	
	/**
	 * 24-26�Ų���ʽ
	 *   UN_OP -> + 
	 *   UN_OP -> - 
	 *   UN_OP -> !
	 * @param op ��Ԫ���������
	 */
	private void reduceUnaryOperator(int op) {
		// ��������ʽ�Ҳ�ķ���
		symbolStack.pop();
		// ����UnaryOperatorType��㲢ѹջ
		symbolStack.push(new UnaryOperator(op));
		// ״̬���Ϸ���1��
		stateStack.pop();
	}
	
	/**
	 * 27-35�Ų���ʽ
	 *   BI_OP1 -> *
	 *   BI_OP2 -> + 
	 *   BI_OP2 -> - 
	 *   BI_OP3 -> < 
	 *   BI_OP3 -> <= 
	 *   BI_OP3 -> > 
	 *   BI_OP3 -> >= 
	 *   BI_OP4 -> == 
	 *   BI_OP4 -> != 
	 * @param op ��Ԫ���������
	 */
	private void reduceBinaryOperator(int op) {
		// ��������ʽ�Ҳ�ķ���
		symbolStack.pop();
		// ����BinaryOperatorType��㲢ѹջ
		symbolStack.push(new BinaryOperator(op));
		// ״̬���Ϸ���1��
		stateStack.pop();
	}
	
}
