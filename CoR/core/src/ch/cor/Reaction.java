package ch.cor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;

/**
 * @author Finini Valentin, Friant Antoine, Meier Christopher, Palumbo Daniel, Stalder Lawrence
 * @date 28.05.2017
 * @brief Reaction représente les objets qui circulent dans la chaine de responsabilité
 */
public class Reaction implements Entity {
    private static final float LIFE_SPAN = 0.5f; // durée d'affichage en secondes
    private static final float WIDTH = 15; // en pixels
    private static Texture texture = new Texture(Gdx.files.internal("lazorv2.png"));
    private float elapsedTime = 0;
    private ColorUtils.Color color;
    private LinkedList<Vector2> links = new LinkedList<Vector2>(); // points de passage du lazer
    private LinkedList<Sprite> sprites = new LinkedList<Sprite>(); // segments graphiques du lazer

    /**
     * Constructeur
     * @param color couleur de départ de la réaction
     */
    public Reaction(ColorUtils.Color color) {
        this.color = color;
    }

    /**
     * Ajoute un lien à la réaction. Cela a pour effet d'afficher un sprite entre le point
     * précédent et le point désigné par l'argument coord
     * @param coord
     */
    public void addLink(Vector2 coord) {
        sprites.add(new Sprite(texture));
        sprites.getLast().setColor(color.getValue());
        links.add(new Vector2(coord));
    }

    @Override
    public void update() {
        elapsedTime += Gdx.graphics.getDeltaTime();

        for(Sprite sp : sprites) {
            // ajuste la transparence des sprites (de plus en plus transparents)
            sp.setAlpha(1 - elapsedTime / LIFE_SPAN);
        }

        // dessine les sprites d'une coordonné à la suivante
        for (int i = 0; i < links.size() - 1; i++) {
            // calcule les différences x et y en les maillons de la chaine
            float x = links.get(i + 1).x - links.get(i).x;
            float y = links.get(i + 1).y - links.get(i).y;

            // étire le sprite
            sprites.get(i).setSize((float) Math.sqrt(x * x + y * y), WIDTH);

            // déclare l'origine du sprite au milieu de son extrêmité gauche
            sprites.get(i).setOrigin(WIDTH / 2, WIDTH / 2);

            // déplace l'origine du sprite sur le lien de départ
            sprites.get(i).setPosition(links.get(i).x, links.get(i).y);

            // effectue une rotation du sprite pour qu'il touche le lien d'arrivée
            sprites.get(i).setRotation(new Vector2(x, y).angle());
        }
    }

    @Override
    public void draw(Batch batch) {
        // dessine tous les segments du lazer
        for (int i = 0; i < sprites.size() -1; i++) {
            sprites.get(i).draw(batch);
        }
    }

    @Override
    public boolean isMarkedForRemoval() {
        return elapsedTime > LIFE_SPAN;
    }

    /**
     * @return la couleur du dernier segment de la réaction
     */
    public ColorUtils.Color getColor() {
        return color;
    }

    /**
     * @param color la nouvelle couleur du dernier segment de la réaction
     */
    public void setColor(ColorUtils.Color color) {
        this.color = color;
        if(!sprites.isEmpty()) {
            // change la couleur du sprite s'il existe déjà (=> s'il y a deux liens ou plus)
            sprites.getLast().setColor(color.getValue());
        }
    }

    /**
     * @return le nombre de liens dans la réaction (maillons de la chaine de responsabilité)
     */
    public int getReactionSize() {
        return links.size();
    }
}
