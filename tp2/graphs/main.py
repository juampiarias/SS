import configparser

if __name__ == "__main__":
    config = configparser.ConfigParser()
    config.read('../configs/app.config')
    config.read(config['DEFAULT']['python'] + config['DEFAULT']['file'])

    # file a leer
    file = config['DEFAULT']['python'] + config['DEFAULT']['output']
    print(file)

    n = config['DEFAULT']['n']
    side = config['DEFAULT']['l']
    v = config['DEFAULT']['v']
    m = config['DEFAULT']['m']
    rc = config['DEFAULT']['rc']
    iteration = config['DEFAULT']['iter']
