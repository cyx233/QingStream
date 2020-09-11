import jieba
from sklearn.feature_extraction.text import  TfidfVectorizer
from sklearn.cluster import KMeans
from collections import Counter
from java import jclass
import copy
import json

def jieba_tokenize(text):
    return jieba.lcut(text)

def cluster_func(news):
    news_content = news.split('QingSteamSplit')
    num_clusters = int(news[0])
    news_content = news_content[1:]
    news_json = []
    for i in range(len(news_content) - 1):
        news_json.append(json.loads(news_content[i]))

    news_content = [new["title"] for new in news_json]

    tfidf_vectorizer = TfidfVectorizer(tokenizer=jieba_tokenize, lowercase=False)
    tfidf_matrix = tfidf_vectorizer.fit_transform(news_content)

    km_cluster = KMeans(n_clusters=num_clusters, max_iter=300, n_init=1, init='k-means++',n_jobs=1)
    result = km_cluster.fit_predict(tfidf_matrix)

    cluster_word = [[] for i in range(num_clusters)]
    for idx in range(len(news_json)):
        for word in news_json[idx]['label']:
            cluster_word[result[idx]].append(word)

    cluster_result = []

    for lst in cluster_word:
        word_counts = Counter(lst)
        cluster_result.append(word_counts.most_common(1)[0][0])

    for i in range(len(news_json)):
        news_json[i]['clusterLabel'] = cluster_result[result[i]]

    s = ""
    for i in range(num_clusters):
        s += cluster_result[i]
        s += "QingClusterSplit"
    s += "QingSteamSplit"
    for news in news_json:
        s += json.dumps(news_json)
        s += "QingNewsSplit"

    return s




