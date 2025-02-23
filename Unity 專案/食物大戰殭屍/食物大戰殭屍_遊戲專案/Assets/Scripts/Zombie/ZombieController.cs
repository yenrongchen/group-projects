using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;

public class ZombieController : MonoBehaviour
{
    [SerializeField]
    private float maxHpMultiplier;

    [SerializeField]
    private float speedMultiplier;

    [SerializeField]
    private float attackMultiplier;

    [SerializeField]
    private GameObject healthBarPrefab;

    [SerializeField]
    private GameObject armPrefab;

    [SerializeField]
    private GameObject headPrefab;

    private float curHP;
    private GameObject healthBarObj;
    private Collider2D food;

    private Animator animator;

    // base attribute
    private float maxHP = 100f;
    private float speed = 0.4f;
    private float attack = 12f;

    private bool isWalking = true;
    private bool isEating = false;
    private bool isDead = false;
    private bool hasBrokenArm = false;
    private bool hasBrokenHead = false;

    void Awake()
    {
        animator = gameObject.GetComponent<Animator>();
        healthBarObj = Instantiate(healthBarPrefab, this.transform.position + new Vector3(-0.1f, 0.75f, 0f), this.transform.rotation);
    }

    void Start()
    {
        maxHP *= maxHpMultiplier;
        speed *= speedMultiplier;
        attack *= attackMultiplier;

        healthBarObj.GetComponent<ZombieHealthBar>().setMaxHP(maxHP);

        curHP = maxHP;
    }

    void Update()
    {
        // moving
        if (isWalking)
        {
            this.transform.position -= new Vector3(speed * Time.deltaTime, 0f, 0f);

            if (curHP > maxHP / 2)  // walk
            {
                animator.SetInteger("state", 0);
            }
            else    // broken_walk
            {
                if (!hasBrokenArm)  // broke the arm
                {
                    Instantiate(armPrefab, this.transform.position + new Vector3(0.05f, 0.05f, 0f), this.transform.rotation);
                    hasBrokenArm = true;
                }

                animator.SetInteger("state", 1);
            }

        }

        // update health bar position
        healthBarObj.transform.position = this.transform.position + new Vector3(-0.1f, 0.75f, 0f);

        // eating
        if (isEating)
        {
            food.GetComponent<FoodSoldier>().Hurt(attack * Time.deltaTime);

            if (curHP > maxHP / 2)  // eat
            {
                animator.SetInteger("state", 2);
            }
            else    // broken_eat
            {
                if (!hasBrokenArm)  // broke the arm
                {
                    Instantiate(armPrefab, this.transform.position + new Vector3(0.05f, 0.05f, 0f), this.transform.rotation);
                    hasBrokenArm = true;
                }

                animator.SetInteger("state", 3);
            }

        }

        // died
        if (curHP == 0 && !hasBrokenHead)
        {
            isWalking = false;
            isDead = true;

            if (isEating)  // eating_die
            {
                animator.SetInteger("state", 5);
            }
            else  // walking_die
            {
                animator.SetInteger("state", 4);
            }

            Destroy(GetComponent<BoxCollider2D>());
            Destroy(this.gameObject, 2f);
            Destroy(healthBarObj, 2f);

            Instantiate(headPrefab, this.transform.position + new Vector3(-0.05f, 0.21f, 0f), this.transform.rotation);
            hasBrokenHead = true;

            isEating = false;
        }

        if (this.transform.position.x < -5.15)
        {
            // TODO Game-over
        }
    }

    // hit by food bullets
    public void Hurt(float dmg)
    {
        if (this.transform.position.x <= 8.8)
        {
            curHP -= dmg;
            curHP = Mathf.Clamp(curHP, 0, maxHP);
            healthBarObj.GetComponent<ZombieHealthBar>().getDamaged(dmg);
            StartCoroutine(changeColor());
        }
    }

    IEnumerator changeColor()
    {
        this.GetComponent<SpriteRenderer>().color = Color.red;
        yield return new WaitForSeconds(0.1f);
        this.GetComponent<SpriteRenderer>().color = Color.white;
    }


    public void OnTriggerEnter2D(Collider2D other)
    {
        if (other.gameObject.tag == "FoodSoldier")  // start attacking
        {
            isWalking = false;
            isEating = true;
            this.food = other;
        }
    }

    public void OnTriggerExit2D(Collider2D other)
    {
        if (isDead)
        {
            return;
        }

        if (other.gameObject.tag == "FoodSoldier")  // continue walking
        {
            isWalking = true;
            isEating = false;
        }
    }

    public void setMultiplier(float hpMul, float spdMul, float atkMul)
    {
        this.maxHpMultiplier = hpMul;
        this.speedMultiplier = spdMul;
        this.attackMultiplier = atkMul;
    }
}
