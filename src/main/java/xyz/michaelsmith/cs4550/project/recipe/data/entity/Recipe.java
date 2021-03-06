package xyz.michaelsmith.cs4550.project.recipe.data.entity;

import xyz.michaelsmith.cs4550.project.user.data.entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String image;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    private String duration;
    private String yield;
    private Date created;

    @OneToMany(mappedBy = "recipe", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<RecipeIngredientMap> ingredients = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<RecipeStep> steps = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<RecipeComment> comments = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getYield() {
        return yield;
    }

    public void setYield(String yield) {
        this.yield = yield;
    }

    public List<RecipeStep> getSteps() {
        return steps;
    }

    public void setSteps(List<RecipeStep> steps) {
        this.steps = steps;
    }

    public void addStep(RecipeStep step) {
        this.steps.add(step);
        step.setRecipe(this);
    }

    public List<RecipeIngredientMap> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeIngredientMap> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(RecipeIngredientMap ingredient) {
        this.ingredients.add(ingredient);
        ingredient.setRecipe(this);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<RecipeComment> getComments() {
        return comments;
    }

    public void setComments(List<RecipeComment> comments) {
        this.comments = comments;
    }
}
