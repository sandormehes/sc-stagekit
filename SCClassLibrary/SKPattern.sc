SKPattern {
    var <name;
    var <builder;
    var <defaults;

    *new { |name, builder, defaults = ()|
        ^super.new.init(name, builder, defaults)
    }

    init { |patternName, buildFunc, defaultArgs|
        name = patternName;
        builder = buildFunc;
        defaults = defaultArgs ? ();
        ^this
    }

    value { |overrides = (), track|
        var merged = IdentityDictionary.new;

        defaults.keysValuesDo { |k, v| merged[k] = v };
        overrides.keysValuesDo { |k, v| merged[k] = v };

        ^builder.value(merged, track)
    }
}