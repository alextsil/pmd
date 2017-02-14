package net.sourceforge.pmd.lang.java.rule.spring;

import net.sourceforge.pmd.lang.java.ast.ASTConstructorDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMarkerAnnotation;
import net.sourceforge.pmd.lang.java.ast.ASTTypeDeclaration;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class EntityShouldContainNoArgsConstructor extends AbstractJavaRule
{

    @Override
    public Object visit ( ASTTypeDeclaration typeDeclaration, Object data )
    {
        List<String> annotationsAsString = typeDeclaration.findDescendantsOfType( ASTMarkerAnnotation.class )
                .stream().map( a -> a.jjtGetLastToken().toString() ).collect( Collectors.toList() );
        if ( annotationsAsString.contains( "Entity" ) )
        {
            List<ASTConstructorDeclaration> constructors =
                    typeDeclaration.findDescendantsOfType( ASTConstructorDeclaration.class );

            if ( constructors.isEmpty() )
            {
                super.addViolation( data, typeDeclaration );
            }

            Optional<ASTConstructorDeclaration> first = constructors
                    .stream().filter( c -> c.getParameterCount() == 0 ).findFirst();
            if ( !first.isPresent() )
            {
                super.addViolation( data, typeDeclaration );
            }
        }
        return super.visit( typeDeclaration, data );
    }
}
