package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;


public class CollidableObject {
    Rectangle hitbox;
boolean isActive = true;
    Texture img;

    /**
     * @param path   a path to the file location
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public CollidableObject(String path, int x, int y, int width, int height) {
        this.img = new Texture(path);
        hitbox = new Rectangle(x, y, width, height);

    }

    public void update() {
    }

    /**
     * draws images and whatnot to the screen
     *
     * @param batch
     */
    public void draw(SpriteBatch batch) {

        batch.draw(img, hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    public void dispose() {
        img.dispose();
        isActive=false;

    }

    /**
     * main is for testing the collisions
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(doIntersect(new Vector2(0, 0), new Vector2(10, 0), new Vector2(5, 5), new Vector2(5, -5)));
        System.out.println("---------");
        System.out.println(findIntersection(new Vector2(0, 0), new Vector2(10, 0), new Vector2(5, 5), new Vector2(5, -5)));
        System.out.println("---------");
        System.out.println(distanceOfIntersection(new Vector2(0, 0), new Vector2(10, 0), new Vector2(5, 5), new Vector2(5, -5)));
        System.out.println("---------");
        System.out.println(minDistanceOfIntersection(new Rectangle(0, 0, 50, 50), new Rectangle(5, 5, 50, 50), new Vector2(2, 3), new Vector2(10, 3)));
        System.out.println("---------");
//System.out.println(checkCollision(new Rectangle(0, 0, 50, 50), new Vector2(3, 3), new Rectangle(3, 3, 50, 50)));
        System.out.println("---------");

    }

    public CollisionInfo moveWithCollision(ArrayList<CollidableObject> objects, Vector2 velocity) {
        if (velocity.x==0 && velocity.y==0){
            return new CollisionInfo();
        }

        boolean doesItIntersect = false;
         CollisionInfo currentCollisionInfo=new CollisionInfo();

         for (int i = 0; i < objects.size(); i++) {
             CollisionInfo collisionInfo = checkCollision(this.hitbox, velocity, objects.get(i));
             if(collisionInfo.side == CollisionInfo.Side.NONE && this.hitbox.overlaps(objects.get(i).hitbox)){
                 collisionInfo.side= CollisionInfo.Side.TOP;
                 collisionInfo.distance =0;
             }
             if (collisionInfo.side != CollisionInfo.Side.NONE) {
                 if (currentCollisionInfo.distance > collisionInfo.distance) {
                     currentCollisionInfo = collisionInfo;
                 }
             }
         }
         if (currentCollisionInfo.side == CollisionInfo.Side.NONE){
             hitbox.x+= velocity.x;
             hitbox.y+= velocity.y;
             return currentCollisionInfo;
         }else {

             float distanceTotal = (float) Math.hypot(velocity.x, velocity.y);
             float distanceTraveled = currentCollisionInfo.distance;

             float ratio = distanceTraveled/distanceTotal;
             float littleX = velocity.x*ratio;
             float littleY = velocity.y*ratio;

             hitbox.x+=(littleX);
             hitbox.y+=(littleY);
             if (currentCollisionInfo.side == CollisionInfo.Side.TOP){

                 hitbox.y = currentCollisionInfo.object.hitbox.y + currentCollisionInfo.object.hitbox.height +.01f;
                 if (distanceTraveled==0) {

//                     ((Player) this).xVelocity =0;
                     return currentCollisionInfo;
                 }
                 moveWithCollision(objects, new Vector2(velocity.x-littleX, 0));
             } else if (currentCollisionInfo.side == CollisionInfo.Side.BOTTOM) {
                 hitbox.y = currentCollisionInfo.object.hitbox.y - hitbox.height - .01f;
                 moveWithCollision(objects, new Vector2(velocity.x-littleX, 0));
             } else if (currentCollisionInfo.side == CollisionInfo.Side.RIGHT) {
                 hitbox.x = currentCollisionInfo.object.hitbox.x + currentCollisionInfo.object.hitbox.width + .01f;
                 moveWithCollision(objects, new Vector2(0,velocity.y-littleY));
             }else {
                 hitbox.x = currentCollisionInfo.object.hitbox.x - hitbox.width - .01f;
                 moveWithCollision(objects, new Vector2(0,velocity.y-littleY));
             }
             return currentCollisionInfo;
         }

    }

    /**
     * returns the point of intersection
     *
     * @param aStart previous first point
     * @param aEnd   end first point
     * @param bStart previous second point
     * @param bEnd   end second point
     * @return returns the intersection point
     */
    public static Vector2 findIntersection(Vector2 aStart, Vector2 aEnd, Vector2 bStart, Vector2 bEnd) {

        if (!doIntersect(aStart, aEnd, bStart, bEnd))
            return null;

        float x1 = aStart.x, y1 = aStart.y;
        float x2 = aEnd.x, y2 = aEnd.y;
        float x3 = bStart.x, y3 = bStart.y;
        float x4 = bEnd.x, y4 = bEnd.y;

        // Calculates the intersection point coordinates
        float x = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) /
                ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));
        float y = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) /
                ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));

        return new Vector2(x, y);
    }

    /**
     * this method checks if there is a collision between the 2 items
     *
     * @param hitboxOld    point of the hitbox before moving
     * @param velocity
     * @param object thing the other object collides with
     * @return returns the min distance and the side of intersection
     */
    public static CollisionInfo checkCollision(Rectangle hitboxOld, Vector2 velocity, CollidableObject object) {
        Rectangle destinationRect = new Rectangle(hitboxOld.x + velocity.x, hitboxOld.y + velocity.y, hitboxOld.width, hitboxOld.height);
        float topDist = minDistanceOfIntersection(hitboxOld, destinationRect, object.hitbox.getTopLeftPoint(), object.hitbox.getTopRightPoint());
        float leftDist = minDistanceOfIntersection(hitboxOld, destinationRect, object.hitbox.getBotLeftPoint(), object.hitbox.getTopLeftPoint());
        float rightDist = minDistanceOfIntersection(hitboxOld, destinationRect, object.hitbox.getBotRightPoint(), object.hitbox.getTopRightPoint());
        float bottomDist = minDistanceOfIntersection(hitboxOld, destinationRect, object.hitbox.getBotLeftPoint(), object.hitbox.getBotRightPoint());
        float minDistance = Float.MAX_VALUE;
        CollisionInfo.Side intersectionSide = CollisionInfo.Side.NONE;
        if (topDist > 0) {
            minDistance = Float.min(minDistance, topDist);
            if (minDistance == topDist) {
                intersectionSide = CollisionInfo.Side.TOP;
            }
        }
        if (leftDist > 0) {
            minDistance = Float.min(minDistance, leftDist);
            if (minDistance == leftDist) {
                intersectionSide = CollisionInfo.Side.LEFT;
            }
        }
        if (rightDist > 0) {
            minDistance = Float.min(minDistance, rightDist);
            if (minDistance == rightDist) {
                intersectionSide = CollisionInfo.Side.RIGHT;
            }
        }
        if (bottomDist > 0) {
            minDistance = Float.min(minDistance, bottomDist);
            if (minDistance == bottomDist) {
                intersectionSide = CollisionInfo.Side.BOTTOM;
            }
        }

        if (minDistance == Float.MAX_VALUE) intersectionSide = CollisionInfo.Side.NONE;
        return new CollisionInfo(minDistance, intersectionSide,object);
    }

    /**
     * finds the minimum distance between the 4 corner points
     *
     * @param hitboxOld         point of the hitbox before moving
     * @param hitboxDestination point of the hitbox after moving
     * @param bStart            previous second point
     * @param bEnd              end second point
     * @return if none intersect it returns -1, if there is an intersection it returns the value.
     */
    public static float minDistanceOfIntersection(Rectangle hitboxOld, Rectangle hitboxDestination, Vector2 bStart, Vector2 bEnd) {
        float bottomLeftDistance = distanceOfIntersection(hitboxOld.getBotLeftPoint(), hitboxDestination.getBotLeftPoint(), bStart, bEnd);
        float topLeftDistance = distanceOfIntersection(hitboxOld.getTopLeftPoint(), hitboxDestination.getTopLeftPoint(), bStart, bEnd);
        float topRightDistance = distanceOfIntersection(hitboxOld.getTopRightPoint(), hitboxDestination.getTopRightPoint(), bStart, bEnd);
        float botRightDistance = distanceOfIntersection(hitboxOld.getBotRightPoint(), hitboxDestination.getBotRightPoint(), bStart, bEnd);

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

        return hasIntersection ? minDistance : -1;
    }

    /**
     * finds the distance of the intersection
     *
     * @param aStart previous first point
     * @param aEnd   end first point
     * @param bStart previous second point
     * @param bEnd   end second point
     * @return -1 if no intersection,else is the distance from aStart to intersection
     */
    public static float distanceOfIntersection(Vector2 aStart, Vector2 aEnd, Vector2 bStart, Vector2 bEnd) {

        float distanceToA = -1;
        // Find the intersection point
        Vector2 intersection = findIntersection(aStart, aEnd, bStart, bEnd);

        // Check if there is an intersection
        if (intersection != null) {
            // Calculate distances from the intersection point to the endpoints of each segment
            distanceToA = intersection.dst(aStart);
        }
        return distanceToA;

    }

    /**
     * Check if two line segments intersect
     *
     * @param aStart previous first point
     * @param aEnd   end first point
     * @param bStart previous second point
     * @param bEnd   end second point
     * @return returns true if there is an intersection
     */

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
//        aStart, aEnd and bStart are collinear and bStart lies on segment aStartaEnd
//        if (o1 == 0 && onSegment(aStart, aEnd, bStart)) return true;
//        aStart, aEnd and bEnd are collinear and bEnd lies on segment aStartaEnd
//        if (o2 == 0 && onSegment(aStart, aEnd, bEnd)) return true;
//        bStart, bEnd and aStart are collinear and aStart lies on segment bStartbEnd
//        if (o3 == 0 && onSegment(bStart, bEnd, aStart)) return true;
//        bStart, bEnd and aEnd are collinear and aEnd lies on segment bStartbEnd
//        if (o4 == 0 && onSegment(bStart, bEnd, aEnd)) return true;
        return false; // Doesn't fall in any of the above cases
    }

    /**
     * Function to find the orientation of triplet (startPoint, testPoint, endPoint).
     * Returns 0 if collinear, 1 if clockwise, and 2 if counterclockwise.
     *
     * @param startPoint the starting point of the line segment.
     * @param testPoint  the point along the line segment that is being tested.
     * @param endPoint   the ending point of the line segment.
     * @return returns a value to help determine if an intersection occurs
     */
    public static double orientation(Vector2 startPoint, Vector2 testPoint, Vector2 endPoint) {
        double val = (testPoint.y - startPoint.y) * (endPoint.x - testPoint.x) - (testPoint.x - startPoint.x) * (endPoint.y - testPoint.y);
        if (val == 0) return 0; // Collinear
        return (val > 0) ? 1 : 2; // Clockwise or counterclockwise
    }
/**
 // Function to check if point q lies on the line segment pr
 public static boolean onSegment(Vector2 p, Vector2 q, Vector2 r) {
 if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) &&
 q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y))
 return true;
 return false;
 }
 **/
}