public class Attack {
    int piercing;
    int slashing;
    int bludgeoning;
    Creature attacker;
    int material_quality = 5;
    int momentum;

    public Attack(int wp, int ws, int wb, Creature attacker) {
        this.piercing = wp * attacker.spearmanship/2 * attacker.strength/2;
        this.slashing = ws * attacker.swordsmanship/2 * attacker.strength/2;
        this.bludgeoning = wb * attacker.strength;
        this.attacker = attacker;
    }

}
