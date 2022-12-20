class WeightedQuickUnion
{
    int[] id;
    int[] size;
    public WeightedQuickUnion(int N)
    {
        id = new int[N];
        size = new int[N];
        for(int i = 0; i < N; i++)
        {
            id[i] = i;
            size[i] = 1;        
        }
    }

    public int root(int i)
    {
        while(i != id[i])
        {
            i = id[i];
        }
        return i;
    }

    public boolean connected(int p, int q)
    {
        return root(p) == root(q);
    }

    public void union(int p, int q)
    {
        int rootp = root(p);
        int rootq = root(q);

        if(rootp == rootq) { return; }
        if(size[rootp] > size[rootq]) { id[rootq] = rootp; size[rootp] += size[rootq]; }
        else                          { id[rootp] = rootq; size[rootq] += size[rootp]; }
    }
}