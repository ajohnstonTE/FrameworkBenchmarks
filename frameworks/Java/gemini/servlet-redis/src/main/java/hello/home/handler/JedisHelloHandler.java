package hello.home.handler;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.google.common.primitives.Ints;
import com.techempower.cache.EntityStore;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiConstants;
import com.techempower.gemini.path.MethodSegmentHandler;
import com.techempower.gemini.path.annotation.PathDefault;
import com.techempower.gemini.path.annotation.PathSegment;

import hello.GhApplication;
import hello.home.entity.Fortune;
import hello.home.entity.RedissonWorld;
import hello.home.entity.World;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

/**
 * Handles the various framework benchmark request types.
 */
public class JedisHelloHandler
    extends  MethodSegmentHandler<Context>
  implements Initialisable
{

  private static final int DB_ROWS = 10000;

  private final EntityStore        store;
  private Jedis jedis;
  private static final String PREFIX = "hello-world::";

  /**
   * Constructor.
   */
  public JedisHelloHandler(GhApplication app)
  {
    super(app, "hllo");
    this.store = app.getStore();
  }

  @Override
  public void initialize()
  {
    jedis = new Jedis();
    store.list(World.class).forEach(it -> {
      String key = PREFIX + it.getId();
      jedis.set(key.getBytes(StandardCharsets.UTF_8),
          Ints.toByteArray(it.getRandomNumber()));
    });
  }
  
  @PathDefault
  public boolean test()
  {
    return false;
  }
  
  /**
   * Return "hello world" as a JSON-encoded message.
   */
  @PathSegment("json")
  public boolean helloworld()
  {
    final Map<String,String> resp = new HashMap<>(1);
    resp.put(GeminiConstants.GEMINI_MESSAGE, "Hello, World!");
    
    return json(resp);
  }

  /**
   * Return a single World objects as JSON, selected randomly from the World
   * table.  Assume the table has 10,000 rows.
   */
  @PathSegment
  public boolean db()
  {
    return json(store.get(World.class, ThreadLocalRandom.current().nextInt(DB_ROWS) + 1));
  }

  /**
   * Return a list of World objects as JSON, selected randomly from the World
   * table.  Assume the table has 10,000 rows.
   */
  @PathSegment("query")
  public boolean multipleQueries()
  {
    final Random random = ThreadLocalRandom.current();
    final int queries = query().getInt("queries", 1, 1, 500);
    final World[] worlds = new World[queries];

    for (int i = 0; i < queries; i++)
    {
      worlds[i] = store.get(World.class, random.nextInt(DB_ROWS) + 1);
    }
    
    return json(worlds);
  }

  /**
   * Return a list of World objects as JSON, selected randomly from the World
   * table.  Assume the table has 10,000 rows.
   */
  @PathSegment("cached_query")
  public boolean multipleCachedQueries()
  {
    final Random random = ThreadLocalRandom.current();
    final int queries = query().getInt("queries", 1, 1, 500);
    final RedissonWorld[] worlds = new RedissonWorld[queries];
    for (int i = 0; i < queries; i++)
    {
      long id = (long)random.nextInt(DB_ROWS) + 1;
      String key = PREFIX + id;
      worlds[i] = new RedissonWorld();
      worlds[i].setId(id);
      worlds[i].setRandomNumber(
          Ints.fromByteArray(jedis.get(key.getBytes(StandardCharsets.UTF_8))));
    }
    return json(worlds);
  }
  
  /**
   * Fetch the full list of Fortunes from the database, sort them by the
   * fortune message text, and then render the results to simple HTML using a 
   * server-side template.
   */
  @PathSegment
  public boolean fortunes()
  {
    final List<Fortune> fortunes = store.list(Fortune.class);
    fortunes.add(new Fortune().setMessage("Additional fortune added at request time."));
    Collections.sort(fortunes);
    return mustache("fortunes", fortunes);
  }

  /**
   * Return a list of World objects as JSON, selected randomly from the World
   * table.  For each row that is retrieved, that row will have its 
   * randomNumber field updated and then the row will be persisted.  We
   * assume the table has 10,000 rows.
   */
  @PathSegment
  public boolean update() {
    final Random random = ThreadLocalRandom.current();
    final int queries = query().getInt("queries", 1, 1, 500);
    final RedissonWorld[] worlds = new RedissonWorld[queries];
    for (int i = 0; i < queries; i++)
    {
      worlds[i] = new RedissonWorld();
      worlds[i].setId(random.nextInt(DB_ROWS) + 1);
      worlds[i].setRandomNumber(random.nextInt(DB_ROWS) + 1);
      String key = PREFIX + worlds[i].getId();
      jedis.set(key.getBytes(StandardCharsets.UTF_8),
          Ints.toByteArray(worlds[i].getRandomNumber()),
          SetParams.setParams().xx());
    }
    return json(worlds);
  }
  
  /**
   * Responds with a plaintext "Hello, World!" 
   */
  @PathSegment
  public boolean plaintext()
  {
    return text("Hello, World!");
  }
}
