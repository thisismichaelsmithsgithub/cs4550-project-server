package xyz.michaelsmith.cs4550.project.recipe.dto.mapper;

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;
import xyz.michaelsmith.cs4550.project.common.dto.mapper.DtoMapper;
import xyz.michaelsmith.cs4550.project.recipe.data.entity.Recipe;
import xyz.michaelsmith.cs4550.project.recipe.data.entity.RecipeComment;
import xyz.michaelsmith.cs4550.project.recipe.data.entity.RecipeIngredientMap;
import xyz.michaelsmith.cs4550.project.recipe.data.entity.RecipeStep;
import xyz.michaelsmith.cs4550.project.recipe.dto.RecipeCommentDto;
import xyz.michaelsmith.cs4550.project.recipe.dto.RecipeDto;
import xyz.michaelsmith.cs4550.project.recipe.dto.RecipeIngredientDto;
import xyz.michaelsmith.cs4550.project.recipe.dto.RecipeStepDto;
import xyz.michaelsmith.cs4550.project.user.data.entity.User;
import xyz.michaelsmith.cs4550.project.user.dto.UserDto;

import static java.util.stream.Collectors.toList;

@Component
public class RecipeDtoMapper implements DtoMapper<Recipe, RecipeDto> {
    private final BoundMapperFacade<Recipe, RecipeDto> mapperFacade;
    private final DtoMapper<User, UserDto> userMapper;
    private final DtoMapper<RecipeIngredientMap, RecipeIngredientDto> ingredientMapper;
    private final DtoMapper<RecipeStep, RecipeStepDto> stepMapper;
    private final DtoMapper<RecipeComment, RecipeCommentDto> commentMapper;

    public RecipeDtoMapper(MapperFactory mapperFactory,
                           DtoMapper<User, UserDto> userMapper,
                           DtoMapper<RecipeIngredientMap, RecipeIngredientDto> ingredientMapper,
                           DtoMapper<RecipeStep, RecipeStepDto> stepMapper,
                           DtoMapper<RecipeComment, RecipeCommentDto> commentMapper) {
        this.userMapper = userMapper;
        this.ingredientMapper = ingredientMapper;
        this.stepMapper = stepMapper;
        this.commentMapper = commentMapper;

        mapperFactory.classMap(Recipe.class, RecipeDto.class)
                        .exclude("author").exclude("ingredients").exclude("steps").exclude("comments")
                        .byDefault().register();
        mapperFacade = mapperFactory.getMapperFacade(Recipe.class, RecipeDto.class);
    }

    @Override
    public RecipeDto map(Recipe entity) {
        RecipeDto dto = mapperFacade.map(entity);
        dto.setAuthor(userMapper.map(entity.getAuthor()));
        dto.getAuthor().setFacebookId(null);
        dto.setIngredients(entity.getIngredients().stream().map(ingredientMapper::map).collect(toList()));
        dto.setSteps(entity.getSteps().stream().map(stepMapper::map).collect(toList()));
        dto.setComments(entity.getComments().stream().map(commentMapper::map).collect(toList()));
        return dto;
    }
}
