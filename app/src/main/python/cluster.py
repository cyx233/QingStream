import jieba
from sklearn.feature_extraction.text import  TfidfVectorizer
from sklearn.cluster import KMeans
from collections import Counter
from java import jclass
import copy

def jieba_tokenize(text):
    return jieba.lcut(text)

def cluster_func(news, num_clusters):
    print("news[0]")
    # news_content = [new["title"] for new in news]
    # tfidf_vectorizer = TfidfVectorizer(tokenizer=jieba_tokenize, lowercase=False)
    # tfidf_matrix = tfidf_vectorizer.fit_transform(news_content)

    # km_cluster = KMeans(n_clusters=num_clusters, max_iter=300, n_init=1, init='k-means++',n_jobs=1)
    # result = km_cluster.fit_predict(tfidf_matrix)
    # cluster_word = [[] for i in range(num_clusters)]
    # for idx in range(news.length):
    #     for word in news[idx].label:
    #         cluster_word[result[idx]].append(word)
    #
    # cluster_result = []
    # for lst in cluster_word:
    #     word_counts = Counter(lst)
    #     cluster_result.append(word_counts.most_common(1)[0][0])
    #
    # ans = jarray(String)(cluster_result)
    # return ans;




