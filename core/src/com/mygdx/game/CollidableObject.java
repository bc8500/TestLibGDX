package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;


public class CollidableObject {
    Rectangle hitbox;
    double health;
    Texture img;

    public CollidableObject(String path, int x, int y, int width, int height) {
        this.img = new Texture(path);
        hitbox = new Rectangle(x, y, width, height);

    }

    public void update() {
    }

    public void draw(SpriteBatch batch) {

        batch.draw(img, hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    public void dispose() {
        img.dispose();
    }



    public static void main(String[] args) {
        System.out.println(doIntersect(new Vector2(0,0), new Vector2(10,0), new Vector2(5,5), new Vector2(5,-5)));
        System.out.println(findIntersection(new Vector2(0,0), new Vector2(10,0), new Vector2(5,5), new Vector2(5,-5)));
        System.out.println(distanceOfIntersection(new Vector2(0,0), new Vector2(10,0), new Vector2(5,5), new Vector2(5,-5)));
        System.out.println(minDistanceOfIntersection( , , , ));

    }




    public static Vector2 findIntersection(Vector2 aStart, Vector2 aEnd, Vector2 bStart, Vector2 bEnd) {
        // If line segments don't intersect, return null
        if (!doIntersect(aStart,aEnd,bStart,bEnd))
            return null;

        float x1 = aStart.x, y1 = aStart.y;
        float x2 = aEnd.x, y2 = aEnd.y;
        float x3 = bStart.x, y3 = bStart.y;
        float x4 = bEnd.x, y4 = bEnd.y;

        // Calculate the intersection point coordinates
        float x = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) /
                ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));
        float y = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) /
                ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));

        return new Vector2(x, y);
    }

    /**
     *
     * @param hitboxOld
     * @param hitboxNew
     * @param bStart
     * @param bEnd
     * @return if none intersect it returns -1, if there is a intersection it returns the value.
     */
    public static float minDistanceOfIntersection(Rectangle hitboxOld,Rectangle hitboxNew, Vector2 bStart, Vector2 bEnd) {
        float bottomLeftDistance = distanceOfIntersection(hitboxOld.getBotLeftPoint(), hitboxNew.getBotLeftPoint(), bStart, bEnd);
        float topLeftDistance = distanceOfIntersection(hitboxOld.getTopLeftPoint(), hitboxNew.getTopLeftPoint(), bStart, bEnd);
        float topRightDistance = distanceOfIntersection(hitboxOld.getTopRightPoint(), hitboxNew.getTopRightPoint(), bStart, bEnd);
        float botRightDistance = distanceOfIntersection(hitboxOld.getBotRightPoint(), hitboxNew.getBotRightPoint(), bStart, bEnd);

        float minDistance = Float.MAX_VALUE;
        boolean hasIntersection = false;
        if (botRightDistance > 0) {
            minDistance = Float.min(minDistance, botRightDistance);
            hasIntersection = true;
        }
        if (bottomLeftDistance > 0) {
            minDistance = Float.min(minDistance, bottomLeftDistance);
            hasIntersection = true;
        }
        if (topRightDistance > 0) {
            minDistance = Float.min(minDistance, topRightDistance);
            hasIntersection = true;
        }
        if (topLeftDistance > 0) {
            minDistance = Float.min(minDistance, topLeftDistance);
            hasIntersection = true;
        }
        return hasIntersection ? minDistance:-1;
    }

    /**
     *
     * @param aStart
     * @param aEnd
     * @param bStart
     * @param bEnd
     * @return -1 if no intersection,else is the distance from aStart to intersection
     */
    public static float distanceOfIntersection(Vector2 aStart, Vector2 aEnd, Vector2 bStart, Vector2 bEnd) {

        float distanceToA= -1;
        // Find the intersection point
        Vector2 intersection = findIntersection(aStart, aEnd, bStart, bEnd);

        // Check if there is an intersection
        if (intersection != null) {
            // Calculate distances from the intersection point to the endpoints of each segment
            distanceToA = intersection.dst2(aStart);
        }
        return distanceToA;

    }
    // Check if two line segments intersect
    public static boolean doIntersect(Vector2 aStart, Vector2 aEnd, Vector2 bStart, Vector2 bEnd) {
        // Find the orientations of points
        double o1 = orientation(aStart, aEnd, bStart);
        double o2 = orientation(aStart, aEnd, bEnd);
        double o3 = orientation(bStart, bEnd, aStart);
        double o4 = orientation(bStart, bEnd, aEnd);

        // General case
        if (o1 != o2 && o3 != o4)
            return true;

        // Special Cases

//        // aStart, aEnd and bStart are collinear and bStart lies on segment aStartaEnd
//        if (o1 == 0 && onSegment(aStart, aEnd, bStart)) return true;
//
//        // aStart, aEnd and bEnd are collinear and bEnd lies on segment aStartaEnd
//        if (o2 == 0 && onSegment(aStart, aEnd, bEnd)) return true;
//
//        // bStart, bEnd and aStart are collinear and aStart lies on segment bStartbEnd
//        if (o3 == 0 && onSegment(bStart, bEnd, aStart)) return true;
//
//        // bStart, bEnd and aEnd are collinear and aEnd lies on segment bStartbEnd
//        if (o4 == 0 && onSegment(bStart, bEnd, aEnd)) return true;

        return false; // Doesn't fall in any of the above cases
    }

    // Function to find the orientation of triplet (p, q, r).
    // Returns 0 if collinear, 1 if clockwise, and 2 if counterclockwise.
    public static double orientation(Vector2 p, Vector2 q, Vector2 r) {
        double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (val == 0) return 0; // Collinear
        return (val > 0) ? 1 : 2; // Clockwise or counterclockwise
    }

    // Function to check if point q lies on line segment pr
    public static boolean onSegment(Vector2 p, Vector2 q, Vector2 r) {
        if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) &&
                q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y))
            return true;
        return false;
    }
}