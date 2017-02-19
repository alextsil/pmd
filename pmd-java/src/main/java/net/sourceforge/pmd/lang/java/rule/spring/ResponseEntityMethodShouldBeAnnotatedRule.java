package net.sourceforge.pmd.lang.java.rule.spring;

import net.sourceforge.pmd.lang.ast.AbstractNode;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceBodyDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTNormalAnnotation;
import net.sourceforge.pmd.lang.java.ast.Token;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

import java.util.List;
import java.util.stream.Collectors;


public class ResponseEntityMethodShouldBeAnnotatedRule extends AbstractJavaRule
{

    @Override
    public Object visit ( ASTClassOrInterfaceBodyDeclaration declaration, Object data )
    {
        String methodReturnTypeClassName = declaration
                .getFirstChildOfType( ASTMethodDeclaration.class ).getResultType().jjtGetFirstToken().toString();
        if ( methodReturnTypeClassName != null )
        {
            if ( methodReturnTypeClassName.equals( "ResponseEntity" ) )
            {
                List<String> annotationsAsString = declaration.findDescendantsOfType( ASTNormalAnnotation.class )
                        .stream()
                        .map( AbstractNode::jjtGetFirstToken )
                        .map( gt -> ( Token )gt ).map( t -> t.next.toString() ).collect( Collectors.toList() );
                if ( !annotationsAsString.contains( "RequestMapping" ) )
                {
                    super.addViolation( data, declaration );
                }
            }
        }
        return super.visit( declaration, data );
    }
}
