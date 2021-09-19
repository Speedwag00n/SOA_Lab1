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
                            <td v-if="movie.screenWriter">Id: {{ movie.screenWriter.id }} ({{ movie.screenWriter.name }})</td>
                            <td v-else>None</td>
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
                    
                    <h3>Pagination</h3>
                    <div>
                        <label for="elementsCount">Elements count:</label>
                        <input id="elementsCount" type="text" maxlength="10" v-model="elementsCount">
                        <label for="pageNumber" class="ml-5">Page number</label>
                        <input id="pageNumber" type="text" maxlength="10" v-model="pageNumber">
                    </div>
                    
                    <h3>Order by</h3>
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
                    
                    <h3>Order direction</h3>
                    <div>
                        <label for="orderDirectionAsc">Asc</label>
                        <input id="orderDirectionAsc" type="radio" value="a" v-model="orderDirection">
                        <label for="orderDirectionDesc" class="pl-3">Desc</label>
                        <input id="orderDirectionDesc" type="radio" value="d" v-model="orderDirection">
                    </div>
                    
                    <h3>Filter</h3>
                    <div>
                        <label for="filterById">Id</label>
                        <input id="filterById" type="text" maxlength="10" v-model="filterById">
                    </div>
                    <div>
                        <label for="filterByName">Name</label>
                        <input id="filterByName" type="text" maxlength="256" v-model="filterByName">
                    </div>
                    <div>
                        <label for="filterByCoordinates">Coordinates</label>
                        <input id="filterByCoordinates" type="text" maxlength="10" v-model="filterByCoordinates">
                    </div>
                    <div>
                        <label for="filterByCreationDate">Creation date</label>
                        <input id="filterByCreationDate" type="text" maxlength="10" v-model="filterByCreationDate">
                        
                        <label for="filterByCreationDateActionLess" class="pl-5"><</label>
                        <input id="filterByCreationDateActionLess" type="radio" value="<" v-model="filterByCreationDateAction" name="filterByCreationDateAction">
                        <label for="filterByCreationDateActionEqual" class="pl-5">==</label>
                        <input id="filterByCreationDateActionEqual" type="radio" value="==" v-model="filterByCreationDateAction" name="filterByCreationDateAction">
                        <label for="filterByCreationDateActionGreater" class="pl-5">\></label>
                        <input id="filterByCreationDateActionGreater" type="radio" value=">" v-model="filterByCreationDateAction" name="filterByCreationDateAction">
                        <label for="filterByCreationDateActionLessEqual" class="pl-5"><=</label>
                        <input id="filterByCreationDateActionLessEqual" type="radio" value="<=" v-model="filterByCreationDateAction" name="filterByCreationDateAction">     
                        <label for="filterByCreationDateActionGreaterEqual" class="pl-5">>=</label>
                        <input id="filterByCreationDateActionGreaterEqual" type="radio" value=">=" v-model="filterByCreationDateAction" name="filterByCreationDateAction">
                    </div>
                    <div>
                        <label for="filterByOscarCount">Oscars count</label>
                        <input id="filterByOscarCount" type="text" maxlength="10" v-model="filterByOscarCount">
                        
                        <label for="filterByOscarCountActionLess" class="pl-5"><</label>
                        <input id="filterByOscarCountActionLess" type="radio" value="<" v-model="filterByOscarCountAction" name="filterByOscarCountAction">
                        <label for="filterByOscarCountActionEqual" class="pl-5">==</label>
                        <input id="filterByOscarCountActionEqual" type="radio" value="==" v-model="filterByOscarCountAction" name="filterByOscarCountAction">
                        <label for="filterByOscarCountActionGreater" class="pl-5">\></label>
                        <input id="filterByOscarCountActionGreater" type="radio" value=">" v-model="filterByOscarCountAction" name="filterByOscarCountAction">
                        <label for="filterByOscarCountActionLessEqual" class="pl-5"><=</label>
                        <input id="filterByOscarCountActionLessEqual" type="radio" value="<=" v-model="filterByOscarCountAction" name="filterByOscarCountAction">     
                        <label for="filterByOscarCountActionGreaterEqual" class="pl-5">>=</label>
                        <input id="filterByOscarCountActionGreaterEqual" type="radio" value=">=" v-model="filterByOscarCountAction" name="filterByOscarCountAction">
                    </div>
                    <div>
                        <label for="filterByGoldenPalmCount">Golden palm count</label>
                        <input id="filterByGoldenPalmCount" type="text" maxlength="10" v-model="filterByGoldenPalmCount">
                        
                        <label for="filterByGoldenPalmCountActionLess" class="pl-5"><</label>
                        <input id="filterByGoldenPalmCountActionLess" type="radio" value="<" v-model="filterByGoldenPalmCountAction" name="filterByGoldenPalmCountAction">
                        <label for="filterByGoldenPalmCountActionEqual" class="pl-5">==</label>
                        <input id="filterByGoldenPalmCountActionEqual" type="radio" value="==" v-model="filterByGoldenPalmCountAction" name="filterByGoldenPalmCountAction">
                        <label for="filterByGoldenPalmCountActionGreater" class="pl-5">\></label>
                        <input id="filterByGoldenPalmCountActionGreater" type="radio" value=">" v-model="filterByGoldenPalmCountAction" name="filterByGoldenPalmCountAction">
                        <label for="filterByGoldenPalmCountActionLessEqual" class="pl-5"><=</label>
                        <input id="filterByGoldenPalmCountActionLessEqual" type="radio" value="<=" v-model="filterByGoldenPalmCountAction" name="filterByGoldenPalmCountAction">     
                        <label for="filterByGoldenPalmCountActionGreaterEqual" class="pl-5">>=</label>
                        <input id="filterByGoldenPalmCountActionGreaterEqual" type="radio" value=">=" v-model="filterByGoldenPalmCountAction" name="filterByGoldenPalmCountAction">
                    </div>
                    <div>
                        <label for="filterByTotalBoxOffice">Total box office</label>
                        <input id="filterByTotalBoxOffice" type="text" maxlength="10" v-model="filterByTotalBoxOffice">
                        
                        <label for="filterByTotalBoxOfficeActionLess" class="pl-5"><</label>
                        <input id="filterByTotalBoxOfficeActionLess" type="radio" value="<" v-model="filterByTotalBoxOfficeAction" name="filterByTotalBoxOfficeAction">
                        <label for="filterByTotalBoxOfficeActionEqual" class="pl-5">==</label>
                        <input id="filterByTotalBoxOfficeActionEqual" type="radio" value="==" v-model="filterByTotalBoxOfficeAction" name="filterByTotalBoxOfficeAction">
                        <label for="filterByTotalBoxOfficeActionGreater" class="pl-5">\></label>
                        <input id="filterByTotalBoxOfficeActionGreater" type="radio" value=">" v-model="filterByTotalBoxOfficeAction" name="filterByTotalBoxOfficeAction">
                        <label for="filterByTotalBoxOfficeActionLessEqual" class="pl-5"><=</label>
                        <input id="filterByTotalBoxOfficeActionLessEqual" type="radio" value="<=" v-model="filterByTotalBoxOfficeAction" name="filterByTotalBoxOfficeAction">     
                        <label for="filterByTotalBoxOfficeActionGreaterEqual" class="pl-5">>=</label>
                        <input id="filterByTotalBoxOfficeActionGreaterEqual" type="radio" value=">=" v-model="filterByTotalBoxOfficeAction" name="filterByTotalBoxOfficeAction">
                    </div>
                    <div>
                        <label for="filterByRating">MPAA Rating</label>
                        
                        <label for="filterByRatingG" class="pl-5">G</label>
                        <input id="filterByRatingG" type="radio" value="G" v-model="filterByRating" name="filterByRating">
                        <label for="filterByRatingPG" class="pl-5">PG</label>
                        <input id="filterByRatingPG" type="radio" value="PG" v-model="filterByRating" name="filterByRating">
                        <label for="filterByRatingPG13" class="pl-5">PG_13</label>
                        <input id="filterByRatingPG13" type="radio" value="PG_13" v-model="filterByRating" name="filterByRating">
                        <label for="filterByRatingNC17" class="pl-5">NC_17</label>
                        <input id="filterByRatingNC17" type="radio" value="NC_17" v-model="filterByRating" name="filterByRating">
                    </div>
                    <div>
                        <label for="filterByScreenWriter">Screen writer</label>
                        <input id="filterByScreenWriter" type="text" maxlength="10" v-model="filterByScreenWriter">
                    </div>
                    
                    <button class="btn btn-info" v-on:click="filter()" type="submit">Filter</button>
                </div>
            `,

        data: function() {
            return {
                elementsCount: '',
                pageNumber: '',
                orderBy: '',
                orderDirection: '',
                filterById: '',
                filterByName: '',
                filterByCoordinates: '',
                filterByCreationDate: '',
                filterByCreationDateAction: '',
                filterByOscarCount: '',
                filterByOscarCountAction: '',
                filterByGoldenPalmCount: '',
                filterByGoldenPalmCountActionAction: '',
                filterByTotalBoxOffice: '',
                filterByTotalBoxOfficeAction: '',
                filterByRating: '',
                filterByScreenWriter: ''
            }
        },
        methods: {
            filter: function () {
                this.$emit('filter', {
                    'elementsCount': this.elementsCount,
                    'pageNumber': this.pageNumber,
                    'orderBy': this.orderBy,
                    'orderDirection': this.orderDirection,
                    'filterById': this.filterById,
                    'filterByName': this.filterByName,
                    'filterByCoordinates': this.filterByCoordinates,
                    'filterByCreationDate': this.filterByCreationDate,
                    'filterByCreationDateAction': this.filterByCreationDateAction,
                    'filterByOscarCount': this.filterByOscarCount,
                    'filterByOscarCountAction': this.filterByOscarCountAction,
                    'filterByGoldenPalmCount': this.filterByGoldenPalmCount,
                    'filterByGoldenPalmCountAction': this.filterByGoldenPalmCountAction,
                    'filterByTotalBoxOffice': this.filterByTotalBoxOffice,
                    'filterByTotalBoxOfficeAction': this.filterByTotalBoxOfficeAction,
                    'filterByRating': this.filterByRating,
                    'filterByScreenWriter': this.filterByScreenWriter
                });
            }
        }
    }
);