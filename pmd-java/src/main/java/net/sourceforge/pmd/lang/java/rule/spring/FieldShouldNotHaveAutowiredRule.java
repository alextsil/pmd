package net.sourceforge.pmd.lang.java.rule.spring;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceBodyDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMarkerAnnotation;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

import java.util.List;
import java.util.stream.Collectors;


public class FieldShouldNotHaveAutowiredRule extends AbstractJavaRule
{

    @Override
    public Object visit ( ASTClassOrInterfaceBodyDeclaration declaration, Object data )
    {
        //Only evaluate fields (not methods/constructors)
        if ( declaration.getFirstChildOfType( ASTFieldDeclaration.class ) != null )
        {
            List<String> annotationsAsString = declaration.findDescendantsOfType( ASTMarkerAnnotation.class )
                    .stream().map( a -> a.jjtGetLastToken().toString() ).collect( Collectors.toList() );
            if ( annotationsAsString.contains( "Autowired" ) || annotationsAsString.contains( "Inject" ) )
            {
                super.addViolation( data, declaration );
            }
        }
        return super.visit( declaration, data );
    }
}
