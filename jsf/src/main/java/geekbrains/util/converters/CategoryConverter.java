package geekbrains.util.converters;

import geekbrains.persist.Category;
import geekbrains.persist.repo.CategoryRepositoryJPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "categoryConverter")
public class CategoryConverter implements Converter {

    private static Logger logger = LoggerFactory.getLogger(CategoryConverter.class);
//    @Override
//    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String category_id) {
//        ValueExpression vex =
//                ctx.getApplication().getExpressionFactory()
//                        .createValueExpression(ctx.getELContext(),
//                                "#{productController}", ProductController.class);
//
//        ProductController productController = (ProductController) vex.getValue(ctx.getELContext());
//        return productController.getCategory(Long.valueOf(category_id));
//    }

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String category_id) {
        logger.info("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
        logger.info("it's CATEGORY CONVERTER, category_id = "+category_id);
        logger.info("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
        ValueExpression vex =
                ctx.getApplication().getExpressionFactory()
                        .createValueExpression(ctx.getELContext(),
                                "#{categoryRepositoryJPA}", CategoryRepositoryJPA.class);

        CategoryRepositoryJPA categoryRepositoryJPA = (CategoryRepositoryJPA) vex.getValue(ctx.getELContext());
        return categoryRepositoryJPA.findById(Long.valueOf(category_id));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object category) {
        return ((Category)category).getId().toString();
    }
}
