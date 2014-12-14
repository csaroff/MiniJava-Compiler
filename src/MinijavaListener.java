// Generated from Minijava.g4 by ANTLR 4.4
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MinijavaParser}.
 */
public interface MinijavaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code thisExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterThisExpression(@NotNull MinijavaParser.ThisExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code thisExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitThisExpression(@NotNull MinijavaParser.ThisExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code variableAssignmentStatement}
	 * labeled alternative in {@link MinijavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterVariableAssignmentStatement(@NotNull MinijavaParser.VariableAssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code variableAssignmentStatement}
	 * labeled alternative in {@link MinijavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitVariableAssignmentStatement(@NotNull MinijavaParser.VariableAssignmentStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code powExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPowExpression(@NotNull MinijavaParser.PowExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code powExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPowExpression(@NotNull MinijavaParser.PowExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddExpression(@NotNull MinijavaParser.AddExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddExpression(@NotNull MinijavaParser.AddExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayInstantiationExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArrayInstantiationExpression(@NotNull MinijavaParser.ArrayInstantiationExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayInstantiationExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArrayInstantiationExpression(@NotNull MinijavaParser.ArrayInstantiationExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinijavaParser#elseBlock}.
	 * @param ctx the parse tree
	 */
	void enterElseBlock(@NotNull MinijavaParser.ElseBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinijavaParser#elseBlock}.
	 * @param ctx the parse tree
	 */
	void exitElseBlock(@NotNull MinijavaParser.ElseBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinijavaParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(@NotNull MinijavaParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinijavaParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(@NotNull MinijavaParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpression(@NotNull MinijavaParser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpression(@NotNull MinijavaParser.AndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ltExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLtExpression(@NotNull MinijavaParser.LtExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ltExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLtExpression(@NotNull MinijavaParser.LtExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinijavaParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(@NotNull MinijavaParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinijavaParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(@NotNull MinijavaParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayAccessExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArrayAccessExpression(@NotNull MinijavaParser.ArrayAccessExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayAccessExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArrayAccessExpression(@NotNull MinijavaParser.ArrayAccessExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nestedStatement}
	 * labeled alternative in {@link MinijavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterNestedStatement(@NotNull MinijavaParser.NestedStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nestedStatement}
	 * labeled alternative in {@link MinijavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitNestedStatement(@NotNull MinijavaParser.NestedStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinijavaParser#mainClass}.
	 * @param ctx the parse tree
	 */
	void enterMainClass(@NotNull MinijavaParser.MainClassContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinijavaParser#mainClass}.
	 * @param ctx the parse tree
	 */
	void exitMainClass(@NotNull MinijavaParser.MainClassContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinijavaParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(@NotNull MinijavaParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinijavaParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(@NotNull MinijavaParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinijavaParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFieldDeclaration(@NotNull MinijavaParser.FieldDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinijavaParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFieldDeclaration(@NotNull MinijavaParser.FieldDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code methodCallExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMethodCallExpression(@NotNull MinijavaParser.MethodCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code methodCallExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMethodCallExpression(@NotNull MinijavaParser.MethodCallExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinijavaParser#localDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterLocalDeclaration(@NotNull MinijavaParser.LocalDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinijavaParser#localDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitLocalDeclaration(@NotNull MinijavaParser.LocalDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifElseStatement}
	 * labeled alternative in {@link MinijavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIfElseStatement(@NotNull MinijavaParser.IfElseStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifElseStatement}
	 * labeled alternative in {@link MinijavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIfElseStatement(@NotNull MinijavaParser.IfElseStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinijavaParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMethodDeclaration(@NotNull MinijavaParser.MethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinijavaParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMethodDeclaration(@NotNull MinijavaParser.MethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinijavaParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclaration(@NotNull MinijavaParser.VarDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinijavaParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclaration(@NotNull MinijavaParser.VarDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinijavaParser#whileBlock}.
	 * @param ctx the parse tree
	 */
	void enterWhileBlock(@NotNull MinijavaParser.WhileBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinijavaParser#whileBlock}.
	 * @param ctx the parse tree
	 */
	void exitWhileBlock(@NotNull MinijavaParser.WhileBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intLitExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIntLitExpression(@NotNull MinijavaParser.IntLitExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intLitExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIntLitExpression(@NotNull MinijavaParser.IntLitExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code booleanLitExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLitExpression(@NotNull MinijavaParser.BooleanLitExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code booleanLitExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLitExpression(@NotNull MinijavaParser.BooleanLitExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinijavaParser#ifBlock}.
	 * @param ctx the parse tree
	 */
	void enterIfBlock(@NotNull MinijavaParser.IfBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinijavaParser#ifBlock}.
	 * @param ctx the parse tree
	 */
	void exitIfBlock(@NotNull MinijavaParser.IfBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSubExpression(@NotNull MinijavaParser.SubExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSubExpression(@NotNull MinijavaParser.SubExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mulExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMulExpression(@NotNull MinijavaParser.MulExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mulExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMulExpression(@NotNull MinijavaParser.MulExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierExpression(@NotNull MinijavaParser.IdentifierExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierExpression(@NotNull MinijavaParser.IdentifierExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNotExpression(@NotNull MinijavaParser.NotExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNotExpression(@NotNull MinijavaParser.NotExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinijavaParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(@NotNull MinijavaParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinijavaParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(@NotNull MinijavaParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayAssignmentStatement}
	 * labeled alternative in {@link MinijavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterArrayAssignmentStatement(@NotNull MinijavaParser.ArrayAssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayAssignmentStatement}
	 * labeled alternative in {@link MinijavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitArrayAssignmentStatement(@NotNull MinijavaParser.ArrayAssignmentStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code printStatement}
	 * labeled alternative in {@link MinijavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterPrintStatement(@NotNull MinijavaParser.PrintStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code printStatement}
	 * labeled alternative in {@link MinijavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitPrintStatement(@NotNull MinijavaParser.PrintStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinijavaParser#goal}.
	 * @param ctx the parse tree
	 */
	void enterGoal(@NotNull MinijavaParser.GoalContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinijavaParser#goal}.
	 * @param ctx the parse tree
	 */
	void exitGoal(@NotNull MinijavaParser.GoalContext ctx);
	/**
	 * Enter a parse tree produced by {@link MinijavaParser#methodBody}.
	 * @param ctx the parse tree
	 */
	void enterMethodBody(@NotNull MinijavaParser.MethodBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinijavaParser#methodBody}.
	 * @param ctx the parse tree
	 */
	void exitMethodBody(@NotNull MinijavaParser.MethodBodyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStatement}
	 * labeled alternative in {@link MinijavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(@NotNull MinijavaParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStatement}
	 * labeled alternative in {@link MinijavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(@NotNull MinijavaParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParenExpression(@NotNull MinijavaParser.ParenExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParenExpression(@NotNull MinijavaParser.ParenExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code objectInstantiationExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterObjectInstantiationExpression(@NotNull MinijavaParser.ObjectInstantiationExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code objectInstantiationExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitObjectInstantiationExpression(@NotNull MinijavaParser.ObjectInstantiationExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayLengthExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArrayLengthExpression(@NotNull MinijavaParser.ArrayLengthExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayLengthExpression}
	 * labeled alternative in {@link MinijavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArrayLengthExpression(@NotNull MinijavaParser.ArrayLengthExpressionContext ctx);
}