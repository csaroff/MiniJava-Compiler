// Generated from Minijava.g4 by ANTLR 4.4
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MinijavaParser}.
 */
public interface MinijavaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MinijavaParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(@NotNull MinijavaParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link MinijavaParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(@NotNull MinijavaParser.ProgContext ctx);
}