package net.sourceforge.pmd.lang.java.rule.spring;

import net.sourceforge.pmd.lang.ast.AbstractNode;
import net.sourceforge.pmd.lang.java.ast.*;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;


public class PathVariableIdentifierShouldMatchRule extends AbstractJavaRule
{

    @Override
    public Object visit ( ASTMethodDeclaration md, Object data )
    {
        List<ASTSingleMemberAnnotation> annotations = md.findDescendantsOfType( ASTSingleMemberAnnotation.class );

        List<String> annotationsStrings = annotations.stream()
                .map( AbstractNode::jjtGetFirstToken )
                .map( gt -> ( Token )gt )
                .map( t -> t.next.toString() )
                .collect( Collectors.toList() );
        int indexOfParameter = annotationsStrings.indexOf( "PathVariable" );
        ASTSingleMemberAnnotation pathVariable = annotations.get( indexOfParameter );

        String pathVariableIdentifierWithQuotes = pathVariable.findChildrenOfType( ASTMemberValue.class )
                .get( 0 ).jjtGetFirstToken().toString();
        String pathVariableIdentifier = StringUtils.remove( pathVariableIdentifierWithQuotes, "\"" );

        String paramIdentifier = md.findDescendantsOfType( ASTVariableDeclaratorId.class )
                .get( indexOfParameter ).getImage();

        if ( !pathVariableIdentifier.equals( paramIdentifier ) )
        {
            super.addViolation( data, md );
        }
        return super.visit( md, data );
    }
}
