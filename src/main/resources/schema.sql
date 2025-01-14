CREATE TABLE IF NOT EXISTS Track (
    id INT NOT NULL,
    artist VARCHAR(255) NOT NULL,
    label VARCHAR(255),
    title VARCHAR(255) NOT NULL,
    trackLength INT,
    releaseYear INT,
    releaseType VARCHAR(255),
    fileFormat VARCHAR(4) NOT NULL,
    sampleRate INT,
    fileLocation VARCHAR(1024) NOT NULL,
    PRIMARY KEY (id)
);