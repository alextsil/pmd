package net.sourceforge.pmd.lang.java.rule.spring;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMarkerAnnotation;
import net.sourceforge.pmd.lang.java.ast.ASTTypeDeclaration;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


//Only for single class or interface declaration per file
public class ClassNameShouldMatchAnnotation extends AbstractJavaRule
{

    @Override
    public Object visit ( ASTTypeDeclaration typeDeclaration, Object data )
    {
        List<String> annotationIdentifiersToCheck = new ArrayList<>();
        annotationIdentifiersToCheck.add( "Controller" );
        annotationIdentifiersToCheck.add( "Service" );
        annotationIdentifiersToCheck.add( "Repository" );

        List<String> annotationsAsString = typeDeclaration.findDescendantsOfType( ASTMarkerAnnotation.class )
                .stream().map( a -> a.jjtGetLastToken().toString() ).collect( Collectors.toList() );

        String classOrInterfaceIdentifier = typeDeclaration.findDescendantsOfType( ASTClassOrInterfaceDeclaration.class )
                .get( 0 ).getImage();

        annotationIdentifiersToCheck.forEach( anId ->
        {
            if ( annotationsAsString.contains( anId ) )
            {
                if ( !classOrInterfaceIdentifier.contains( anId ) )
                {
                    super.addViolation( data, typeDeclaration );
                }
            }
        } );

        return super.visit( typeDeclaration, data );
    }

}
