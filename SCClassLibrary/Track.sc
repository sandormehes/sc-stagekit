Track {
    var <name;
    var <engine;
    var <patternSource;
    var <player;
    var <bus;
    var <group;
    var <synthDefName;
    var <volume = 1.0;
    var <isMuted = false;
    var <isPlaying = false;
    var <params;

    *new { |name, patternSource = nil, synthDefName = nil, bus = nil|
        ^super.new.init(name, patternSource, synthDefName, bus)
    }

    init { |trackName, patternObj, synthName, audioBus|
        name = trackName;
        patternSource = patternObj;
        synthDefName = synthName;
        bus = audioBus;
        params = IdentityDictionary.new;
        ^this
    }

    pattern_ { |aPattern|
        patternSource = aPattern;
        ^patternSource
    }

    play { |quant|
        var p = this.buildPattern;

        p.isNil.if {
            ("SKTrack: no pattern for track " ++ name).warn;
            ^nil
        };

        player = p.play(
            clock: engine.clock,
            quant: quant ? engine.quant
        );
        isPlaying = true;
        ^player
    }

    stop {
        player.notNil.if(_.stop);
        player = nil;
        isPlaying = false;
        ^this
    }

    mute {
        isMuted = true;
        ^this
    }

    unmute {
        isMuted = false;
        ^this
    }

    toggleMute {
        isMuted = isMuted.not;
        ^isMuted
    }

    set { |key, value|
        params[key] = value;
        ^value
    }

    at { |key|
        ^params[key]
    }

    volume_ { |value|
        volume = value.clip(0, 1);
        ^volume
    }

    buildPattern {
        patternSource.isNil.if { ^nil };

        patternSource.isKindOf(Function).if {
            ^patternSource.value(this)
        };

        ^patternSource
    }

    applyConfig { |config|
        config.isNil.if {
            this.stop;
            ^this
        };

        config[\pattern].notNil.if {
            this.pattern = config[\pattern];
        };

        config[\volume].notNil.if {
            this.volume = config[\volume];
        };

        config[\mute].notNil.if {
            isMuted = config[\mute];
        };

        config.keysValuesDo { |k, v|
            if([\pattern, \volume, \mute].includes(k).not) {
                this.set(k, v);
            }
        };

        ^this
    }
}