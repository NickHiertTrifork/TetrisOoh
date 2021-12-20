package main.drawables.entity.unit.component;

import javafx.geometry.Point2D;
import main.drawables.entity.Targetable;

public abstract class WeaponComponent implements UnitComponent{

    double range;
    double damage;
    double speed;

    public WeaponComponent(double range, double damage, double speed) {

    }

    public boolean canAttack(Point2D origin, Targetable target, double timeSinceLastAttack) {
        if(isInRange(origin, target) && timeSinceLastAttack >= (1 / speed) ) {
            return true;
        }
        return false;
    }

    private boolean isInRange(Point2D origin, Targetable target) {
        return false;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
}
