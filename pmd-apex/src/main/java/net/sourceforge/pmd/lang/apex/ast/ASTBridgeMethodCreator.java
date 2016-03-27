/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd.lang.apex.ast;

import apex.jorje.semantic.ast.member.bridge.BridgeMethodCreator;

public class ASTBridgeMethodCreator extends AbstractApexNode<BridgeMethodCreator> {

	public ASTBridgeMethodCreator(BridgeMethodCreator bridgeMethodCreator) {
		super(bridgeMethodCreator);
	}

    /**
     * Accept the visitor.
     * Note: This needs to be in each concrete node class, as otherwise
     * the visitor won't work - as java resolves the type "this" at compile
     * time.
     */
    public Object jjtAccept(ApexParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}
