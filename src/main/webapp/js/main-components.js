Vue.component(
    'result-table',
    {
        template:
            `
                <div>
                    <h1 class="col-xs-1 text-center">Movies</h1>
                    <table class="table table-striped table-bordered table-hover p-5 text-center" v-bind:class="{empty: isEmpty}">
                        <tr>
                            <th>Id</th>
                            <th>Name</th>
                            <th>Coordinates</th>
                            <th>Creation date</th>
                            <th>Oscars count</th>
                            <th>Golden palm count</th>
                            <th>Total box office</th>
                            <th>MPAA rating</th>
                            <th>Screen writer</th>
                            <th>Actions</th>
                        </tr>
                        <tr v-for="movie in movies">
                            <td>{{ movie.id }}</td>
                            <td>{{ movie.name }}</td>
                            <td>Id: {{ movie.coordinates.id }} (X: {{ movie.coordinates.x }}. Y: {{ movie.coordinates.y }})</td>
                            <td>{{ movie.creationDate }}</td>
                            <td>{{ movie.oscarsCount }}</td>
                            <td>{{ movie.goldenPalmCount }}</td>
                            <td>{{ movie.totalBoxOffice }}</td>
                            <td>{{ movie.mpaaRating }}</td>
                            <td>Id: {{ movie.screenWriter.id }} ({{ movie.screenWriter.name }})</td>
                            <td><button class="btn btn-danger" v-on:click="deleteMovie(movie)" type="submit">Delete</button></td>
                        </tr>
                    </table>
                </div>
            `,

        props: ["movies"],
        computed: {
            isEmpty: function () {
                return this.movies.length == 0;
            }
        },
        methods: {
            deleteMovie: function (movie) {
                this.$emit('deletemovie', movie);
            }
        }
    }
);

Vue.component(
    'filters',
    {
        template:
            `
                <div>
                    <h1 class="col-xs-1 text-center">Options</h1>
                    <h2>Pagination</h2>
                    <div>
                        <label for="elementsCount">Elements count:</label>
                        <input id="elementsCount" type="text" maxlength="10" v-model="elementsCount">
                        <label for="pageNumber" class="ml-5">Page number</label>
                        <input id="pageNumber" type="text" maxlength="10" v-model="pageNumber">
                    </div>
                    <h2>Order by</h2>
                    <div>
                        <label for="orderById">Id</label>
                        <input id="orderById" type="radio" value="id" v-model="orderBy">
                        <label for="orderByName" class="pl-3">Name</label>
                        <input id="orderByName" type="radio" value="name" v-model="orderBy">
                        <label for="orderByCoordinates" class="pl-3">Coorinates id</label>
                        <input id="orderByCoordinates" type="radio" value="coordinates" v-model="orderBy">
                        <label for="orderByCreationDate" class="pl-3">Creation date</label>
                        <input id="orderByCreationDate" type="radio" value="creationDate" v-model="orderBy">     
                        <label for="orderByOscarsCount" class="pl-3">Oscars count</label>
                        <input id="orderByOscarsCount" type="radio" value="oscarsCount" v-model="orderBy">
                        <label for="orderByGoldenPalmCount" class="pl-3">Golden palm count</label>
                        <input id="orderByGoldenPalmCount" type="radio" value="goldenPalmCount" v-model="orderBy">
                        <label for="orderByTotalBoxOffice" class="pl-3">Total box office</label>
                        <input id="orderByTotalBoxOffice" type="radio" value="totalBoxOffice" v-model="orderBy">
                        <label for="orderByMPAARating" class="pl-3">MPAA Rating</label>
                        <input id="orderByMPAARating" type="radio" value="mpaaRating" v-model="orderBy">
                        <label for="orderByScreenWriter" class="pl-3">Screen writer</label>
                        <input id="orderByScreenWriter" type="radio" value="screenWriter" v-model="orderBy">
                        <label for="orderByNone" class="pl-3">None</label>
                        <input id="orderByNone" type="radio" value="none" v-model="orderBy" checked>
                    </div>
                    <h2>Order direction</h2>
                    <div>
                        <label for="orderDirectionAsc">Asc</label>
                        <input id="orderDirectionAsc" type="radio" value="a" v-model="orderDirection" checked>
                        <label for="orderDirectionDesc" class="pl-3">Desc</label>
                        <input id="orderDirectionDesc" type="radio" value="d" v-model="orderDirection">
                    </div>
                    <button class="btn btn-info" v-on:click="filter()" type="submit">Filter</button>
                </div>
            `,

        data: function() {
            return {
                elementsCount: '',
                pageNumber: '',
                orderBy: '',
                orderDirection: ''
            }
        },
        methods: {
            filter: function () {
                this.$emit('filter', {'elementsCount': this.elementsCount, 'pageNumber': this.pageNumber, 'orderBy': this.orderBy, 'orderDirection': this.orderDirection});
            }
        }
    }
);