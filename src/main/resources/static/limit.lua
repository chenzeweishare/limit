local key = KEYS[1] --限流key
local limit = tonumber(ARGV[1]) --限流大小
local current = tonumber(redis.call("INCRBY", key , 1))  --请求数+1
if current > limit then --如果超出限流大小
    return 0
elseif current == 1 then -- 只有第一次访问需要设置2秒的过期的时间
    redis.call("expire", key, "2");
end
    return 1