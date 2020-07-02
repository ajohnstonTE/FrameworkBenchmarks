package hello.home.entity;

import org.redisson.api.annotation.REntity;
import org.redisson.api.annotation.RId;

import com.techempower.js.legacy.Visitor;
import com.techempower.js.legacy.VisitorFactory;
import com.techempower.js.legacy.Visitors;

@REntity
public class RedissonWorld
{
  @RId
  private long id;
  private int randomNumber;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  /**
   * Set the random number.
   */
  public void setRandomNumber(int randomNumber)
  {
    this.randomNumber = randomNumber;
  }

  /**
   * Get the random number.
   */
  public int getRandomNumber()
  {
    return this.randomNumber;
  }

  /**
   * A visitor factory used to map this class to JSON.
   */
  public static final VisitorFactory<RedissonWorld> VISITOR_FACTORY = new VisitorFactory<RedissonWorld>()
  {
    @Override
    public Visitor visitor(RedissonWorld world)
    {
      return Visitors.map(
          "id", world.getId(),
          "randomNumber", world.getRandomNumber());
    }
  };
}
