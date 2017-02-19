package net.sourceforge.pmd.lang.java.rule.spring;

import net.sourceforge.pmd.lang.ast.AbstractNode;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceBodyDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTNormalAnnotation;
import net.sourceforge.pmd.lang.java.ast.Token;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

import java.util.List;
import java.util.stream.Collectors;


public class RequestMappingMethodShouldBePublicRule extends AbstractJavaRule
{

    @Override
    public Object visit ( ASTClassOrInterfaceBodyDeclaration declaration, Object data )
    {
        List<String> annotationsAsString = declaration.findDescendantsOfType( ASTNormalAnnotation.class )
                .stream()
                .map( AbstractNode::jjtGetFirstToken )
                .map( gt -> ( Token )gt ).map( t -> t.next.toString() ).collect( Collectors.toList() );

        if ( annotationsAsString.contains( "RequestMapping" ) )
        {
            if ( !declaration.getFirstChildOfType( ASTMethodDeclaration.class ).isPublic() )
            {
                super.addViolation( data, declaration );
            }
        }
        return super.visit( declaration, data );
    }
}
